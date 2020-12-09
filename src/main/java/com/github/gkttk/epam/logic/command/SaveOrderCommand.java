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

    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final OrderService orderService;

    public SaveOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        List<Dish> orderDishes = (List<Dish>) requestDataHolder.getSessionAttribute("orderDishes");
        List<Long> dishIds = orderDishes.stream().map(Dish::getId).collect(Collectors.toList());


        BigDecimal orderCost = (BigDecimal) requestDataHolder.getSessionAttribute("orderCost");

        User authUser = (User) requestDataHolder.getSessionAttribute("authUser");
        Long userId = authUser.getId();

        String date = requestDataHolder.getRequestParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Order order = new Order(null, orderCost, dateTime, true, userId);

        orderService.makeOrder(order, dishIds);

        requestDataHolder.putSessionAttribute("orderMessage", "Your order has been accepted");//todo i18n

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, MENU_PAGE);

        return new CommandResult(MENU_PAGE, true);
    }
}
