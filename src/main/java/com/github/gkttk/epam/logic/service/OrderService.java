package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    void makeOrder(BigDecimal orderCost, LocalDateTime dateTime, long userId, List<Long> dishIds) throws ServiceException;

    List<Order> getAllByUserId(Long userId) throws ServiceException;

    boolean takeOrder(Order order, User user) throws ServiceException;

    List<Order> getAllActiveWithExpiredDate() throws ServiceException;

    void blockOrder(Order order) throws ServiceException;

    void cancelOrder(Order order, User user) throws ServiceException;

    List<Order> getAllActiveByUserId(Long userId) throws ServiceException;


}
