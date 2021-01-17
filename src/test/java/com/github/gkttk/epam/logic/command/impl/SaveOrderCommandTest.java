package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.DishType;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaveOrderCommandTest {

    private final static String BASKET_ATTR = "basket";
    private final static String ORDER_COST_ATTR = "orderCost";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String DATE_PARAM = "date";
    private final static String MESSAGE_ATTR = "message";
    private final static String MESSAGE = "order.message.accepted";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String ERROR_MESSAGE_VALUE = "error.message.wrong.date";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private OrderService orderServiceMock;
    private Validator dataValidatorMock;

    private Command saveOrderCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.orderServiceMock = Mockito.mock(OrderService.class);
        this.dataValidatorMock = Mockito.mock(Validator.class);

        this.saveOrderCommand = new SaveOrderCommand(orderServiceMock, dataValidatorMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMenuPageIfDateParamIsValid() throws ServiceException {
        //given
        LocalDateTime date = LocalDateTime.MIN;
        String dateParam = String.valueOf(date);
        List<Dish> basket = new ArrayList<>();
        basket.add(new Dish(1L, "name1", DishType.SALAD, new BigDecimal(10), "img1Base64"));
        basket.add(new Dish(2L, "name2", DishType.BEVERAGE, new BigDecimal(20), "img2Base64"));
        basket.add(new Dish(3L, "name3", DishType.SOUP, new BigDecimal(30), "img3Base64"));

        List<Long> dishIds = basket.stream().map(Dish::getId).collect(Collectors.toList());

        BigDecimal orderCost = basket.stream().map(Dish::getCost).reduce(BigDecimal::add).get();
        long userId = TEST_USER.getId();

        CommandResult expectedResult = new CommandResult(MENU_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(DATE_PARAM)).thenReturn(dateParam);
        when(dataValidatorMock.validate(dateParam)).thenReturn(true);
        when(requestDataHolderMock.getSessionAttribute(BASKET_ATTR)).thenReturn(basket);
        when(requestDataHolderMock.getSessionAttribute(ORDER_COST_ATTR)).thenReturn(orderCost);
        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        //when
        CommandResult result = saveOrderCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DATE_PARAM);
        verify(dataValidatorMock).validate(dateParam);
        verify(requestDataHolderMock).getSessionAttribute(BASKET_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(ORDER_COST_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(orderServiceMock).makeOrder(orderCost, date, userId, dishIds);
        verify(requestDataHolderMock).putSessionAttribute(BASKET_ATTR, null);
        verify(requestDataHolderMock).putSessionAttribute(MESSAGE_ATTR, MESSAGE);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, MENU_PAGE);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToMenuPageIfDateParamIsNotValid() throws ServiceException {
        //given
        LocalDateTime date = LocalDateTime.MIN;
        String dateParam = String.valueOf(date);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, false);

        when(requestDataHolderMock.getRequestParameter(DATE_PARAM)).thenReturn(dateParam);
        when(dataValidatorMock.validate(dateParam)).thenReturn(false);
        //when
        CommandResult result = saveOrderCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DATE_PARAM);
        verify(dataValidatorMock).validate(dateParam);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_VALUE);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, MENU_PAGE);

        assertEquals(expectedResult, result);
    }


}