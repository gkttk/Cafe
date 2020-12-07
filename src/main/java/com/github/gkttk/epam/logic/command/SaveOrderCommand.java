package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SaveOrderCommand implements Command {

    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    private final OrderService orderService;

    public SaveOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        List<Dish> orderDishes = (List<Dish>) session.getAttribute("orderDishes");
        List<Long> dishIds = orderDishes.stream().map(Dish::getId).collect(Collectors.toList());


        BigDecimal orderCost = (BigDecimal) session.getAttribute("orderCost");

        User authUser = (User) session.getAttribute("authUser");
        Long userId = authUser.getId();

        String date = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Order order = new Order(null, orderCost, dateTime, true, userId);

        orderService.makeOrder(order, dishIds);

        session.setAttribute("orderMessage", "Your order has been accepted");//todo i18n

        return new CommandResult(MENU_PAGE, true);
    }
}
