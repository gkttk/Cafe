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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TakeOrderCommandTest {

    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String ORDER_ID_PARAM = "orderId";
    private final static String ORDERS_ATTR = "orders";
    private final static String ERROR_MESSAGE_ATTR = "noMoneyErrorMessage";
    private final static String ERROR_MESSAGE = "error.message.no.money";
    private final static OrderSortType ACTIVE_ORDER_SORT_TYPE = OrderSortType.ACTIVE;

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private UserService userServiceMock;
    private OrderService orderServiceMock;

    private Command takeOrderCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);
        this.orderServiceMock = Mockito.mock(OrderService.class);
        this.takeOrderCommand = new TakeOrderCommand(orderServiceMock, userServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMyOrdersPageWhenUserCanPayForOrder() throws ServiceException {
        //given
        String orderIdParam = "3";
        long orderId = Long.parseLong(orderIdParam);
        long userId = TEST_USER.getId();

        Order takenOrder = new Order(orderId, new BigDecimal(10), LocalDateTime.MIN, userId);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, new BigDecimal(15), LocalDateTime.MIN, userId));
        orders.add(new Order(2L, new BigDecimal(20), LocalDateTime.MIN, userId));
        orders.add(takenOrder);

        List<Order> newOrders = Arrays.asList(null, null, null);

        BigDecimal newUserMoney = TEST_USER.getMoney().subtract(takenOrder.getCost());
        User changedUser = new User(1L, "testLogin", "testPassword", UserRole.USER,
                50, newUserMoney, false, "imgBase64Test");

        when(requestDataHolderMock.getRequestParameter(ORDER_ID_PARAM)).thenReturn(orderIdParam);
        when(requestDataHolderMock.getSessionAttribute(ORDERS_ATTR)).thenReturn(orders);
        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(orderServiceMock.takeOrder(takenOrder, TEST_USER)).thenReturn(true);
        when(orderServiceMock.getAllActiveByUserIdAndStatus(userId, ACTIVE_ORDER_SORT_TYPE)).thenReturn(newOrders);
        when(userServiceMock.getById(userId)).thenReturn(Optional.of(changedUser));

        CommandResult expectedResult = new CommandResult(MY_ORDERS_PAGE, true);
        //when
        CommandResult result = takeOrderCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(ORDER_ID_PARAM);
        verify(requestDataHolderMock).getSessionAttribute(ORDERS_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(orderServiceMock).takeOrder(takenOrder, TEST_USER);
        verify(orderServiceMock).getAllActiveByUserIdAndStatus(userId, ACTIVE_ORDER_SORT_TYPE);
        verify(requestDataHolderMock).putSessionAttribute(ORDERS_ATTR, newOrders);
        verify(userServiceMock).getById(userId);
        verify(requestDataHolderMock).putSessionAttribute(AUTH_USER_ATTR, changedUser);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToMyOrdersPageWhenUserCanNotPayForOrder() throws ServiceException {
        //given
        String orderIdParam = "3";
        long orderId = Long.parseLong(orderIdParam);
        long userId = TEST_USER.getId();

        Order takenOrder = new Order(orderId, new BigDecimal(10), LocalDateTime.MIN, userId);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, new BigDecimal(15), LocalDateTime.MIN, userId));
        orders.add(new Order(2L, new BigDecimal(20), LocalDateTime.MIN, userId));
        orders.add(takenOrder);

        when(requestDataHolderMock.getRequestParameter(ORDER_ID_PARAM)).thenReturn(orderIdParam);
        when(requestDataHolderMock.getSessionAttribute(ORDERS_ATTR)).thenReturn(orders);
        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(orderServiceMock.takeOrder(takenOrder, TEST_USER)).thenReturn(false);

        CommandResult expectedResult = new CommandResult(MY_ORDERS_PAGE, false);
        //when
        CommandResult result = takeOrderCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(ORDER_ID_PARAM);
        verify(requestDataHolderMock).getSessionAttribute(ORDERS_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(orderServiceMock).takeOrder(takenOrder, TEST_USER);
        verify(requestDataHolderMock).putRequestAttribute(ERROR_MESSAGE_ATTR, ERROR_MESSAGE);

        assertEquals(expectedResult, result);
    }


}