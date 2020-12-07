package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.connection.ConnectionPool;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.impl.OrderServiceImpl;
import com.github.gkttk.epam.logic.service.impl.UserServiceImpl;
import com.github.gkttk.epam.model.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class main {

    public static void main(String[] args) throws ServiceException {
        Long user_id = 1L;
        Order order = new Order(null, new BigDecimal(15), LocalDateTime.now(), true,1L);




        OrderService orderService = new OrderServiceImpl();
        orderService.makeOrder(order, Arrays.asList(1L,2L,3L));
    }
}
