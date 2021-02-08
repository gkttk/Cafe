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

public class MyOrdersPageCommandTest {

    private final static String MY_ORDERS_PAGE = "/WEB-INF/view/my_orders_page.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String ORDERS_ATTR = "orders";
    private final static String SORT_TYPE_PARAM = "sortType";

    private final static User TEST_USER = new User(1L, "testLogin", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private OrderService orderServiceMock;

    private Command myOrdersPageCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.orderServiceMock = Mockito.mock(OrderService.class);
        this.myOrdersPageCommand = new MyOrdersPageCommand(orderServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToMyOrdersPage() throws ServiceException {
        //given
        long userId = TEST_USER.getId();

        String sortTypeParam = OrderSortType.ACTIVE.name();
        OrderSortType sortType = OrderSortType.valueOf(sortTypeParam);

        List<Order> orders = Arrays.asList(null, null, null);

        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(requestDataHolderMock.isRequestParamContainsKey(SORT_TYPE_PARAM)).thenReturn(true);
        when(requestDataHolderMock.getRequestParameter(SORT_TYPE_PARAM)).thenReturn(sortTypeParam);
        when(orderServiceMock.getAllActiveByUserIdAndStatus(userId, sortType)).thenReturn(orders);

        CommandResult expectedResult = new CommandResult(MY_ORDERS_PAGE, false);
        //when
        CommandResult result = myOrdersPageCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(requestDataHolderMock).isRequestParamContainsKey(SORT_TYPE_PARAM);
        verify(requestDataHolderMock).getRequestParameter(SORT_TYPE_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(ORDERS_ATTR, orders);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, MY_ORDERS_PAGE);

        assertEquals(expectedResult, result);
    }


}