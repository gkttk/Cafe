package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MyOrdersPageCommand implements Command {

    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final OrderService orderService;

    public MyOrdersPageCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        User authUser = (User)session.getAttribute("authUser");
        Long userId = authUser.getId();


        List<Order> orders = orderService.getAllOrdersByUserId(userId);

        session.setAttribute("orders", orders);

        session.setAttribute(CURRENT_PAGE_PARAMETER, MY_ORDERS_PAGE);

        return new CommandResult(MY_ORDERS_PAGE, true);

    }
}
