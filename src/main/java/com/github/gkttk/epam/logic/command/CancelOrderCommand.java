package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CancelOrderCommand implements Command {

    private final OrderService orderService;
    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static int PENALTY = 10;

    public CancelOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute("authUser");
        int userPoints = authUser.getPoints();

        int newUserPoints = userPoints - PENALTY;

        String orderIdParam = requestDataHolder.getRequestParameter("orderId");
        long orderId = Long.parseLong(orderIdParam);

        List<Order> orders = (List<Order>) requestDataHolder.getSessionAttribute("orders");

        Optional<Order> updateOrderOpt = orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();

        if (updateOrderOpt.isPresent()) {
            Order order = updateOrderOpt.get();
            boolean newStatus = false;

            Order newOrder = getOrderWithNewStatus(order, newStatus);
            User newUser = getUserWithNewPoints(authUser, newUserPoints);

            orderService.takeOrder(newOrder, newUser);

            orders.remove(order);
            orders.add(newOrder);

            requestDataHolder.putSessionAttribute("orders", orders);


            requestDataHolder.putSessionAttribute("authUser", newUser);

        }



        return new CommandResult(MY_ORDERS_PAGE, true);

    }



    private Order getOrderWithNewStatus(Order oldOrder, boolean newCondition){
        Long id = oldOrder.getId();
        BigDecimal orderCost = oldOrder.getCost();
        LocalDateTime time = oldOrder.getTime();
        Long userId = oldOrder.getUserId();


        return new Order(id, orderCost, time, newCondition, userId);

    }

    private User getUserWithNewPoints(User oldAuthUser, int newPoints) {
        Long userId = oldAuthUser.getId();
        String login = oldAuthUser.getLogin();
        String password = oldAuthUser.getPassword();
        UserRole role = oldAuthUser.getRole();
        BigDecimal money = oldAuthUser.getMoney();
        boolean active = oldAuthUser.isActive();
        String imageRef = oldAuthUser.getImageRef();

        return new User(userId, login, password, role, newPoints, money, active, imageRef);


    }


}