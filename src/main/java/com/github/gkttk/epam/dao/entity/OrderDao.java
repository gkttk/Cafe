package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Order;

import java.util.List;

public interface OrderDao extends Dao<Order> {

    void saveOrderDish(long orderId, long dishId) throws DaoException;

    List<Order> findAllActiveWithExpiredDate() throws DaoException;

    List<Order> findAllActiveByUserId(long userId) throws DaoException;

    List<Order> findAllNotActiveByUserId(long userId) throws DaoException;


}
