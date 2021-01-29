package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactoryImpl;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.service.impl.OrderServiceImpl;
import com.github.gkttk.epam.model.dto.OrderInfo;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class AjaxController extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(AjaxController.class);
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
        Gson gson = new Gson();
        String result = gson.toJson(MESSAGE);
        try {
            Optional<OrderInfo> orderInfoOpt = orderService.getOrderInfo(orderId);
            if (orderInfoOpt.isPresent()) {
                OrderInfo orderInfo = orderInfoOpt.get();
                result = gson.toJson(orderInfo);
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't get order info from ajax controller", e);
        } finally {
            response.getWriter().write(result);
        }
    }


}
