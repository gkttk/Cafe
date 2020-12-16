package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;

public class OrderServiceImpl implements OrderService {


    @Override
    public void makeOrder(Order order, List<Long> dishIds) throws ServiceException {//todo
        DaoHelper daoHelper = DaoHelperFactory.createDaoHelper();
        Long userId = order.getUserId();
        try {
            OrderDao orderDao = daoHelper.createOrderDao();
            daoHelper.startTransaction();
            Long savedOrderId = orderDao.save(order);
            for (Long dishId : dishIds) {
                orderDao.saveOrderDish(savedOrderId, dishId);
            }
            daoHelper.commit();
        } catch (DaoException e) {
            try {
                daoHelper.rollback();
            } catch (DaoException ex) {
                throw new ServiceException(String.format("Can't rollback() in makeOrder with order: %s, user_id: %d",
                        order.toString(), userId), e);
            }
        } finally {
            try {
                daoHelper.endTransaction();
                daoHelper.close();
            } catch (DaoException e) {
                throw new ServiceException(String.format("Can't endTransaction() in makeOrder with order: %s, user_id: %d",
                        order.toString(), userId), e);
            }
        }


    }

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            return orderDao.findAllByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllOrdersByUserId()  with userId: %d",
                    userId), e);
        }
    }

    @Override
    public void takeOrder(Order order, User user) throws ServiceException {
        DaoHelper daoHelper = DaoHelperFactory.createDaoHelper();
        try {
            OrderDao orderDao = daoHelper.createOrderDao();
            UserDao userDao = daoHelper.createUserDao();
            daoHelper.startTransaction();
            orderDao.save(order);
            userDao.save(user);
            daoHelper.commit();

        } catch (DaoException e) {
            try {
                daoHelper.rollback();
            } catch (DaoException ex) {
                throw new ServiceException(String.format("Can't rollback() in takeOrder with order: %s, user: %s",
                        order.toString(), user.toString()), e);
            }
            throw new ServiceException(String.format("Can't takeOrder()  with order: %s",
                    order.toString()), e);
        } finally {
            try {
                daoHelper.endTransaction();
                daoHelper.close();
            } catch (DaoException e) {
                throw new ServiceException(String.format("Can't endTransaction() in takeOrder with order: %s, user: %s",
                        order.toString(), user.toString()), e);
            }
        }
    }
}
