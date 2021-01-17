package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.builder.OrderBuilder;
import com.github.gkttk.epam.model.builder.UserBuilder;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderSortType;
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

    private final DaoHelperFactory daoHelperFactory;

    public OrderServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public void makeOrder(BigDecimal orderCost, LocalDateTime dateTime, long userId, List<Long> dishIds)
            throws ServiceException {
        Order order = new Order(null, orderCost, dateTime, userId);

        DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper();
        OrderDao orderDao = daoHelperImpl.createOrderDao();
        try {
            daoHelperImpl.startTransaction();
            long savedOrderId = orderDao.save(order);
            for (long dishId : dishIds) {
                orderDao.saveOrderDish(savedOrderId, dishId);
            }
            daoHelperImpl.commit();
        } catch (DaoException e) {
            try {
                daoHelperImpl.rollback();
            } catch (DaoException ex) {
                LOGGER.warn("Can't rollback() in makeOrder with order: {}, user_id: {}", order, userId, ex);
            }
            throw new ServiceException(String.format("Can't makeOrder(orderCost, dateTime, userId, dishIds) " +
                            "with orderCost: %f, dateTime: %s, userId: %d, dishIds size: %d",
                    orderCost, dateTime, userId, dishIds.size()), e);
        } finally {
            try {
                daoHelperImpl.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in makeOrder with order: {}, user_id: {}",
                        order, userId, exception);
            }
            daoHelperImpl.close();
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

        DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper();
        OrderDao orderDao = daoHelperImpl.createOrderDao();
        UserDao userDao = daoHelperImpl.createUserDao();
        try {
            daoHelperImpl.startTransaction();
            orderDao.save(changedOrder);
            userDao.save(changedUser);
            daoHelperImpl.commit();
        } catch (DaoException e) {
            try {
                daoHelperImpl.rollback();
            } catch (DaoException ex) {
                LOGGER.warn("Can't rollback() in takeOrder with order: {}, user: {}", order, user, ex);
            }
            throw new ServiceException(String.format("Can't takeOrder()  with order: %s", order), e);
        } finally {
            try {
                daoHelperImpl.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in takeOrder with order: {}, user: {}",
                        order, user, exception);
            }
            daoHelperImpl.close();
        }
        return true;
    }

    @Override
    public List<Order> getAllActiveWithExpiredDate() throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            OrderDao orderDao = daoHelperImpl.createOrderDao();
            return orderDao.findAllActiveWithExpiredDate();
        } catch (DaoException e) {
            throw new ServiceException("Can't getAllActiveWithExpiredDate()", e);
        }
    }

    @Override
    public void blockOrder(Order order) throws ServiceException {
        DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper();
        try {
            daoHelperImpl.startTransaction();
            OrderBuilder orderBuilder = order.builder();
            orderBuilder.setStatus(OrderStatus.BLOCKED);
            Order newOrder = orderBuilder.build();
            OrderDao orderDao = daoHelperImpl.createOrderDao();
            orderDao.save(newOrder);

            long userId = order.getUserId();
            UserDao userDao = daoHelperImpl.createUserDao();
            Optional<User> userOpt = userDao.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                UserBuilder builder = user.builder();
                builder.setPoints(user.getPoints() - DEFAULT_PENALTY);
                User newUser = builder.build();
                userDao.save(newUser);
            }
            daoHelperImpl.commit();

        } catch (DaoException e) {
            try {
                daoHelperImpl.rollback();
            } catch (DaoException ex) {
                LOGGER.warn("Can't rollback() in blockOrder(order) with order: {}", order, ex);
            }
            throw new ServiceException(String.format("Can't blockOrder(order)  with order: %s", order.toString()), e);
        } finally {
            try {
                daoHelperImpl.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in  blockOrder(order) with order: {}", order, exception);
            }

            daoHelperImpl.close();
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


        DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper();
        OrderDao orderDao = daoHelperImpl.createOrderDao();
        UserDao userDao = daoHelperImpl.createUserDao();

        try {
            daoHelperImpl.startTransaction();
            orderDao.save(changedOrder);
            userDao.save(changedUser);
            daoHelperImpl.commit();

        } catch (DaoException e) {
            try {
                daoHelperImpl.rollback();
            } catch (DaoException ex) {
                LOGGER.warn("Can't rollback() in cancelOrder(order, user) with order: {}, user: {}",
                        order, user, ex);
            }
            throw new ServiceException(String.format("Can't cancelOrder(order, user)  with order: %s, user: %s",
                    order, user), e);
        } finally {
            try {
                daoHelperImpl.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in cancelOrder(order, user) with order: {}, user: {}",
                        order, user, exception);
            }
            daoHelperImpl.close();
        }
    }

    @Override
    public List<Order> getAllActiveByUserIdAndStatus(long userId, OrderSortType sortType) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            OrderDao orderDao = daoHelperImpl.createOrderDao();
            return sortType.getOrders(orderDao, userId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllActiveByUserIdAndStatus(userId, sortType)  with" +
                    " userId: %d, sortType: %s", userId, sortType.name()), e);
        }
    }

}
