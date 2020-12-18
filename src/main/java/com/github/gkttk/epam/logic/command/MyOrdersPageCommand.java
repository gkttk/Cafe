package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;

public class MyOrdersPageCommand implements Command {

    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final OrderService orderService;

    public MyOrdersPageCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute("authUser");
        Long userId = authUser.getId();


        List<Order> orders = orderService.getAllOrdersByUserId(userId);

        requestDataHolder.putSessionAttribute("orders", orders);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, MY_ORDERS_PAGE);

        return new CommandResult(MY_ORDERS_PAGE, true);

    }
}
