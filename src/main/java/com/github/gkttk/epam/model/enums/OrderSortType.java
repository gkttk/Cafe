package com.github.gkttk.epam.model.enums;

import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Order;

import java.util.List;

public enum OrderSortType {
    ACTIVE {
        public List<Order> getOrders(OrderDao orderDao, long userId) throws DaoException {
            return orderDao.findAllActiveByUserId(userId);
        }
    },
    EXPIRED {
        public List<Order> getOrders(OrderDao orderDao, long userId) throws DaoException {
            return orderDao.findAllNotActiveByUserId(userId);
        }
    };


    public abstract List<Order> getOrders(OrderDao orderDao, long userId) throws DaoException;
}
