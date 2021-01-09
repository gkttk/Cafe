package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.builder.OrderBuilder;
import com.github.gkttk.epam.model.builder.UserBuilder;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderSortTypes;
import com.github.gkttk.epam.model.enums.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final static Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);
    private final static int DEFAULT_PENALTY = 10;
    private final static int DEFAULT_BONUS = 15;

    @Override
    public void makeOrder(BigDecimal orderCost, LocalDateTime dateTime, long userId, List<Long> dishIds) throws ServiceException {
        Order order = new Order(null, orderCost, dateTime, userId);

        DaoHelper daoHelper = DaoHelperFactory.createDaoHelper();
        OrderDao orderDao = daoHelper.createOrderDao();
        try {
            daoHelper.startTransaction();
            long savedOrderId = orderDao.save(order);
            for (long dishId : dishIds) {
                orderDao.saveOrderDish(savedOrderId, dishId);
            }
            daoHelper.commit();
        } catch (DaoException e) {
            try {
                daoHelper.rollback();
            } catch (DaoException ex) {
                throw new ServiceException(String.format("Can't rollback() in makeOrder with order: %s, user_id: %d",
                        order, userId), e);
            }
        } finally {
            try {
                daoHelper.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in makeOrder with order: {}, user_id: {}", order, userId, exception);
            }
            daoHelper.close();
        }


    }


    @Override
    public boolean takeOrder(Order order, User user) throws ServiceException {
        BigDecimal userMoney = user.getMoney();
        BigDecimal orderCost = order.getCost();

        if (userMoney.compareTo(orderCost) < 0) {
            return false;
        }

        OrderBuilder orderBuilder = order.builder();
        orderBuilder.setStatus(OrderStatus.RETRIEVED);
        Order changedOrder = orderBuilder.build();

        int userPoints = user.getPoints();
        int newUserPoints = userPoints + DEFAULT_BONUS;

        BigDecimal newUserMoney = userMoney.subtract(orderCost);
        UserBuilder userBuilder = user.builder();
        userBuilder.setPoints(newUserPoints);
        userBuilder.setMoney(newUserMoney);
        User changedUser = userBuilder.build();

        DaoHelper daoHelper = DaoHelperFactory.createDaoHelper();
        OrderDao orderDao = daoHelper.createOrderDao();
        UserDao userDao = daoHelper.createUserDao();
        try {
            daoHelper.startTransaction();
            orderDao.save(changedOrder);
            userDao.save(changedUser);
            daoHelper.commit();
        } catch (DaoException e) {
            try {
                daoHelper.rollback();
            } catch (DaoException ex) {
                throw new ServiceException(String.format("Can't rollback() in takeOrder with order: %s, user: %s",
                        order, user), ex);
            }
            throw new ServiceException(String.format("Can't takeOrder()  with order: %s", order), e);
        } finally {
            try {
                daoHelper.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in takeOrder with order: {}, user: {}", order, user, exception);
            }
            daoHelper.close();
        }
        return true;
    }

    @Override
    public List<Order> getAllActiveWithExpiredDate() throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            return orderDao.findAllActiveWithExpiredDate();
        } catch (DaoException e) {
            throw new ServiceException("Can't getAllActiveWithExpiredDate()", e);
        }
    }

    @Override
    public void blockOrder(Order order) throws ServiceException {
        DaoHelper daoHelper = DaoHelperFactory.createDaoHelper();
        try {
            daoHelper.startTransaction();
            OrderBuilder orderBuilder = order.builder();
            orderBuilder.setStatus(OrderStatus.BLOCKED);
            Order newOrder = orderBuilder.build();
            OrderDao orderDao = daoHelper.createOrderDao();
            orderDao.save(newOrder);

            long userId = order.getUserId();
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> userOpt = userDao.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                UserBuilder builder = user.builder();
                builder.setPoints(user.getPoints() - DEFAULT_PENALTY);
                User newUser = builder.build();
                userDao.save(newUser);
            }

            daoHelper.commit();

        } catch (DaoException e) {
            try {
                daoHelper.rollback();
            } catch (DaoException ex) {
                throw new ServiceException(String.format("Can't rollback() in blockOrder(order) with order: %s",
                        order.toString()), ex);
            }
            throw new ServiceException(String.format("Can't blockOrder(order)  with order: %s", order.toString()), e);
        } finally {
            try {
                daoHelper.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in  blockOrder(order) with order: {}", order, exception);
            }
            daoHelper.close();
        }
    }

    @Override
    public void cancelOrder(Order order, User user) throws ServiceException {

        OrderBuilder orderBuilder = order.builder();
        orderBuilder.setStatus(OrderStatus.CANCELLED);
        Order changedOrder = orderBuilder.build();

        int userPoints = user.getPoints();
        int newUserPoints = userPoints - DEFAULT_PENALTY;

        UserBuilder userBuilder = user.builder();
        userBuilder.setPoints(newUserPoints);
        User changedUser = userBuilder.build();


        DaoHelper daoHelper = DaoHelperFactory.createDaoHelper();
        OrderDao orderDao = daoHelper.createOrderDao();
        UserDao userDao = daoHelper.createUserDao();

        try {
            daoHelper.startTransaction();
            orderDao.save(changedOrder);
            userDao.save(changedUser);
            daoHelper.commit();

        } catch (DaoException e) {
            try {
                daoHelper.rollback();
            } catch (DaoException ex) {
                throw new ServiceException(String.format("Can't rollback() in cancelOrder(order, user) with order: %s, user: %s",
                        order, user), ex);
            }
        } finally {
            try {
                daoHelper.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in cancelOrder(order, user) with order: {}, user: {}",
                        order, user, exception);
            }
            daoHelper.close();
        }
    }

    @Override
    public List<Order> getAllActiveByUserIdAndStatus(Long userId, OrderSortTypes sortType) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            return sortType.getOrders(orderDao, userId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllActiveByUserIdAndStatus(userId, sortType)  with userId: %d," +
                    " sortType: %s", userId, sortType.name()), e);
        }
    }

}
