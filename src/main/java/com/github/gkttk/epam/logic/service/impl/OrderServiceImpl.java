package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.entities.Order;

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
}
