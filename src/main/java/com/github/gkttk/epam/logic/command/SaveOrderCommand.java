package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.handler.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SaveOrderCommand implements Command {
    private final static String BASKET_ATTRIBUTE = "basket";
    private final static String ORDER_COST_ATTRIBUTE = "orderCost";
    private final static String AUTH_USER_ATTRIBUTE = "authUser";
    private final static String DATE_PARAMETER = "date";
    private final static String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    private final static String ORDER_MESSAGE_ATTRIBUTE = "orderMessage";
    private final static String ORDER_MESSAGE = "Your order has been accepted"; //todo i18n

    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    private final OrderService orderService;

    public SaveOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        List<Dish> basket = (List<Dish>) requestDataHolder.getSessionAttribute(BASKET_ATTRIBUTE);
        List<Long> dishIds = basket.stream()
                .map(Dish::getId)
                .collect(Collectors.toList());

        BigDecimal orderCost = (BigDecimal) requestDataHolder.getSessionAttribute(ORDER_COST_ATTRIBUTE);

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTRIBUTE);
        Long userId = authUser.getId();

        String date = requestDataHolder.getRequestParameter(DATE_PARAMETER);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Order order = new Order(null, orderCost, dateTime, true, userId);

        orderService.makeOrder(order, dishIds);

        requestDataHolder.putSessionAttribute(BASKET_ATTRIBUTE, null);

        requestDataHolder.putSessionAttribute(ORDER_MESSAGE_ATTRIBUTE, ORDER_MESSAGE);//todo delete from session

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, MENU_PAGE);

        return new CommandResult(MENU_PAGE, true);
    }
}
