package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;

public class MyOrdersPageCommand implements Command {
    private final OrderService orderService;

    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String ORDERS_ATTR = "orders";


    public MyOrdersPageCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        List<Order> orders = orderService.getAllByUserId(userId);

        requestDataHolder.putSessionAttribute(ORDERS_ATTR, orders);
        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, MY_ORDERS_PAGE);

        return new CommandResult(MY_ORDERS_PAGE, true);

    }
}
