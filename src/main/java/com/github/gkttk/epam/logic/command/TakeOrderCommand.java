package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderStatus;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TakeOrderCommand implements Command {


    private final OrderService orderService;
    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static int BONUS = 15;


    public TakeOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute("authUser");
        BigDecimal userMoney = authUser.getMoney();

        int points = authUser.getPoints();
        int newUserPoints = points + BONUS;


        String orderIdParam = requestDataHolder.getRequestParameter("orderId");
        long orderId = Long.parseLong(orderIdParam);

        List<Order> orders = (List<Order>) requestDataHolder.getSessionAttribute("orders");

        Optional<Order> updateOrderOpt = orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();

        if (updateOrderOpt.isPresent()) {

            Order order = updateOrderOpt.get();
            Long id = order.getId();
            BigDecimal orderCost = order.getCost();
            LocalDateTime time = order.getDate();
            OrderStatus status = OrderStatus.RETRIEVED;
            Long userId = order.getUserId();


            if (userMoney.compareTo(orderCost) < 0) {
                requestDataHolder.putRequestAttribute("noMoneyErrorMessage", "Not enough money!");
                return new CommandResult(MY_ORDERS_PAGE, false);
            }

            BigDecimal newUserMoney = userMoney.subtract(orderCost);

            User newUser = getUserWithNewMoneyAndNewPoints(authUser, newUserMoney, newUserPoints);

            Order newOrder = new Order(id, orderCost, time, status, userId);

            orderService.takeOrder(newOrder, newUser);

            orders.remove(order);
            orders.add(newOrder);

            requestDataHolder.putSessionAttribute("orders", orders);


            requestDataHolder.putSessionAttribute("authUser", newUser);

        }

        return new CommandResult(MY_ORDERS_PAGE, true);
    }


    private User getUserWithNewMoneyAndNewPoints(User oldAuthUser, BigDecimal newMoney, int newPoints) {
        Long userId = oldAuthUser.getId();
        String login = oldAuthUser.getLogin();
        String password = oldAuthUser.getPassword();
        UserRole role = oldAuthUser.getRole();
        boolean active = oldAuthUser.isBlocked();
        String imageRef = oldAuthUser.getImageRef();


        return new User(userId, login, password, role, newPoints, newMoney, active, imageRef);


    }


}
