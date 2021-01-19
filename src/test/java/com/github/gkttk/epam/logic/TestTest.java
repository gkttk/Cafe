package com.github.gkttk.epam.logic;

import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactoryImpl;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.service.impl.OrderServiceImpl;
import com.github.gkttk.epam.model.dto.OrderInfo;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TestTest {

    @Test
    public void  asdasd() throws DaoException, ServiceException {
        DaoHelperFactory daoHelperFactory = new DaoHelperFactoryImpl();
        OrderService orderService = new OrderServiceImpl(daoHelperFactory);
        Optional<OrderInfo> orderInfoOpt = orderService.getOrderInfo(15);
        OrderInfo orderInfo = orderInfoOpt.get();

        System.out.println(orderInfo.getCost());
        System.out.println(orderInfo.getDate());
        for (String name: orderInfo.getDishNames()){
            System.out.println(name);
        }
    }
}
