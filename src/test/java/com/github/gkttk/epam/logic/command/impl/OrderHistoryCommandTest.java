package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderSortType;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderHistoryCommandTest {

    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String ORDERS_ATTR = "orders";
    private final static OrderSortType NOT_ACTIVE_ORDER_SORT_TYPE = OrderSortType.EXPIRED;

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private OrderService orderServiceMock;

    private Command orderHistoryCommand;
    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.orderServiceMock = Mockito.mock(OrderService.class);
        this.orderHistoryCommand = new OrderHistoryCommand(orderServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMyOrdersPage() throws ServiceException {
        //given
        long userId = TEST_USER.getId();
        List<Order> orders = Arrays.asList(null, null, null);

        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(orderServiceMock.getAllActiveByUserIdAndStatus(userId, NOT_ACTIVE_ORDER_SORT_TYPE)).thenReturn(orders);

        CommandResult expectedResult = new CommandResult(MY_ORDERS_PAGE, true);
        //when
        CommandResult result = orderHistoryCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(orderServiceMock).getAllActiveByUserIdAndStatus(userId, NOT_ACTIVE_ORDER_SORT_TYPE);
        verify(requestDataHolderMock).putSessionAttribute(ORDERS_ATTR, orders);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, MY_ORDERS_PAGE);

        assertEquals(expectedResult, result);
    }


}