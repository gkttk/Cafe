package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderSortType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for working with Order entity.
 */
public interface OrderService {

    void makeOrder(BigDecimal orderCost, LocalDateTime dateTime, long userId, List<Long> dishIds) throws ServiceException;

    boolean takeOrder(Order order, User user) throws ServiceException;

    List<Order> getAllActiveWithExpiredDate() throws ServiceException;

    void blockOrder(Order order) throws ServiceException;

    void cancelOrder(Order order, User user) throws ServiceException;

    List<Order> getAllActiveByUserIdAndStatus(long userId, OrderSortType sortType) throws ServiceException;

}
