package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.service.impl.OrderServiceImpl;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderSortTypes;

import java.util.List;

public class OrderHistoryCommand implements Command {

    private final OrderService orderService;
    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String ORDERS_ATTR = "orders";
    private final static OrderSortTypes NOT_ACTIVE_ORDER_SORT_TYPE = OrderSortTypes.EXPIRED;


    public OrderHistoryCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();
        List<Order> orders = orderService.getAllActiveByUserIdAndStatus(userId,NOT_ACTIVE_ORDER_SORT_TYPE);

        requestDataHolder.putSessionAttribute(ORDERS_ATTR, orders);
        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, MY_ORDERS_PAGE);

        return new CommandResult(MY_ORDERS_PAGE, true);
    }
}
