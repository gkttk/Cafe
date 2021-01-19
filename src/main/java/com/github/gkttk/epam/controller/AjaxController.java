package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactoryImpl;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.service.impl.OrderServiceImpl;
import com.github.gkttk.epam.model.dto.OrderInfo;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class AjaxController extends HttpServlet {


    private final static String MESSAGE = "Incorrect data";
    private final static String ORDER_ID_PARAM = "orderId";

    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        DaoHelperFactory daoHelperFactory = new DaoHelperFactoryImpl();
        this.orderService = new OrderServiceImpl(daoHelperFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderIdParam = request.getParameter(ORDER_ID_PARAM);
        long orderId = Long.parseLong(orderIdParam);

        try {
            Optional<OrderInfo> orderInfoOpt = orderService.getOrderInfo(orderId);
            String result = MESSAGE;
            if (orderInfoOpt.isPresent()) {
                Gson gson = new Gson();
                OrderInfo orderInfo = orderInfoOpt.get();
                result = gson.toJson(orderInfo);
            }
            response.getWriter().write(result);

        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }


}
