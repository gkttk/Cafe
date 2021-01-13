package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderSortType;

import java.util.List;
import java.util.Optional;

public class TakeOrderCommand implements Command {


    private final OrderService orderService;
    private final UserService userService;

    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String ORDER_ID_PARAM = "orderId";
    private final static String ORDERS_ATTR = "orders";
    private final static String ERROR_MESSAGE_ATTR = "noMoneyErrorMessage";
    private final static String ERROR_MESSAGE = "error.message.no.money";

    private final static OrderSortType ACTIVE_ORDER_SORT_TYPE = OrderSortType.ACTIVE;


    public TakeOrderCommand(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        Optional<Order> orderOpt = getOrderFromSession(requestDataHolder);
        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            boolean isOrderTaken = orderService.takeOrder(order, authUser);

            if (!isOrderTaken) {
                requestDataHolder.putRequestAttribute(ERROR_MESSAGE_ATTR, ERROR_MESSAGE);
                return new CommandResult(MY_ORDERS_PAGE, false);
            }
        }

        long userId = authUser.getId();
        renewSession(requestDataHolder, userId);
        return new CommandResult(MY_ORDERS_PAGE, true);
    }

    private Optional<Order> getOrderFromSession(RequestDataHolder requestDataHolder) {
        String orderIdParam = requestDataHolder.getRequestParameter(ORDER_ID_PARAM);
        long orderId = Long.parseLong(orderIdParam);

        List<Order> orders = (List<Order>) requestDataHolder.getSessionAttribute(ORDERS_ATTR);
        return orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();
    }


    private void renewSession(RequestDataHolder requestDataHolder, long userId) throws ServiceException {
        List<Order> userOrders = orderService.getAllActiveByUserIdAndStatus(userId, ACTIVE_ORDER_SORT_TYPE);
        requestDataHolder.putSessionAttribute(ORDERS_ATTR, userOrders);

        Optional<User> userOpt = userService.getById(userId);
        userOpt.ifPresent(user -> requestDataHolder.putSessionAttribute(AUTH_USER_ATTR, user));
    }


}
