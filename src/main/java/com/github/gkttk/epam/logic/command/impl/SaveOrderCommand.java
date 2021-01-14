package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SaveOrderCommand implements Command {

    private final OrderService orderService;
    private final Validator dataValidator;

    private final static String BASKET_ATTR = "basket";
    private final static String ORDER_COST_ATTR = "orderCost";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String DATE_PARAM = "date";
    private final static String MESSAGE_ATTR = "message";
    private final static String MESSAGE = "order.message.accepted";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String ERROR_MESSAGE_KEY = "errorMessage";
    private final static String ERROR_MESSAGE_VALUE = "error.message.wrong.date";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";


    public SaveOrderCommand(OrderService orderService, Validator dataValidator) {
        this.orderService = orderService;
        this.dataValidator = dataValidator;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String dateParam = requestDataHolder.getRequestParameter(DATE_PARAM);
        boolean isDateValid = dataValidator.validate(dateParam);
        if (!isDateValid) {
            requestDataHolder.putRequestAttribute(ERROR_MESSAGE_KEY, ERROR_MESSAGE_VALUE);
            requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, MENU_PAGE);
            return new CommandResult(MENU_PAGE, false);
        }

        LocalDateTime dateTime = LocalDateTime.parse(dateParam);

        List<Long> dishIds = getDishIds(requestDataHolder);
        BigDecimal orderCost = (BigDecimal) requestDataHolder.getSessionAttribute(ORDER_COST_ATTR);

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        orderService.makeOrder(orderCost, dateTime, userId, dishIds);

        requestDataHolder.putSessionAttribute(BASKET_ATTR, null);
        requestDataHolder.putSessionAttribute(MESSAGE_ATTR, MESSAGE);
        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, MENU_PAGE);

        return new CommandResult(MENU_PAGE, true);
    }


    private List<Long> getDishIds(RequestDataHolder requestDataHolder) {
        List<Dish> basket = (List<Dish>) requestDataHolder.getSessionAttribute(BASKET_ATTR);
        return basket.stream()
                .map(Dish::getId)
                .collect(Collectors.toList());
    }

}
