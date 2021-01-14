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
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CancelOrderCommandTest {

    private final static String AUTH_USER_ATTR = "authUser";
    private final static String ORDER_ID_PARAM = "orderId";
    private final static String ORDERS_ATTR = "orders";
    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static OrderSortType ACTIVE_ORDER_SORT_TYPE = OrderSortType.ACTIVE;

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private OrderService orderServiceMock;
    private UserService userServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command cancelOrderCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.orderServiceMock = Mockito.mock(OrderService.class);
        this.userServiceMock = Mockito.mock(UserService.class);

        this.cancelOrderCommand = new CancelOrderCommand(orderServiceMock, userServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMyOrderPage() throws ServiceException {
        //given
        Order cancelledOrder = new Order(3L, new BigDecimal(3), LocalDateTime.MIN, TEST_USER.getId());
        String orderIdParam = "3";
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, new BigDecimal(1), LocalDateTime.MIN, TEST_USER.getId()));
        orders.add(new Order(2L, new BigDecimal(2), LocalDateTime.MIN, TEST_USER.getId()));
        orders.add(cancelledOrder);

        long userId = TEST_USER.getId();

        List<Order> newOrders = new ArrayList<>(orders);
        newOrders.removeIf(order -> order.getId().equals(cancelledOrder.getId()));

        when(requestDataHolderMock.getRequestParameter(ORDER_ID_PARAM)).thenReturn(orderIdParam);
        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(requestDataHolderMock.getSessionAttribute(ORDERS_ATTR)).thenReturn(orders);
        when(userServiceMock.getById(userId)).thenReturn(Optional.of(TEST_USER));
        when(orderServiceMock.getAllActiveByUserIdAndStatus(userId, ACTIVE_ORDER_SORT_TYPE)).thenReturn(newOrders);

        CommandResult expectedResult = new CommandResult(MY_ORDERS_PAGE, true);
        //when
        CommandResult result = cancelOrderCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(ORDER_ID_PARAM);
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(ORDERS_ATTR);
        verify(orderServiceMock).cancelOrder(cancelledOrder, TEST_USER);
        verify(userServiceMock).getById(userId);
        verify(requestDataHolderMock).putSessionAttribute(AUTH_USER_ATTR, TEST_USER);
        verify(orderServiceMock).getAllActiveByUserIdAndStatus(userId, ACTIVE_ORDER_SORT_TYPE);
        verify(requestDataHolderMock).putSessionAttribute(ORDERS_ATTR, newOrders);

        assertEquals(expectedResult, result);
    }


}