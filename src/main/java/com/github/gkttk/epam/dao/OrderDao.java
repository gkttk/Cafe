package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Order;

import java.util.List;

public interface OrderDao extends Dao<Order> {

    void saveOrderDish(Long orderId, Long dishId) throws DaoException;//todo

    List<Order> findAllByUserId(Long userId) throws DaoException;



}
