package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;

public interface OrderService {

    void makeOrder(Order order,List<Long> dishIds) throws ServiceException;

    List<Order> getAllOrdersByUserId(Long userId) throws ServiceException;

    void takeOrder(Order order, User user) throws ServiceException;


}
