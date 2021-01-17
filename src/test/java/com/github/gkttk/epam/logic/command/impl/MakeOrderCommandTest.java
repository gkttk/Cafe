package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MakeOrderCommandTest {

    private final static String BASKET_ATTR = "basket";
    private final static String ORDER_COST_ATTR = "orderCost";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String MESSAGE_ATTR = "message";
    private final static String ERROR_MESSAGE = "error.message.empty.basket";
    private final static String MAKE_ORDER_PAGE = "/WEB-INF/view/make_order.jsp";

    private Command makeOrderCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.makeOrderCommand = new MakeOrderCommand();
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMakeOrderPageWhenBasketNotEmpty() throws ServiceException {
        //given
        List<Dish> basket = new ArrayList<>();
        basket.add(new Dish(1L, "name1", DishType.SALAD, new BigDecimal(10), "img1Base64"));
        basket.add(new Dish(2L, "name2", DishType.BEVERAGE, new BigDecimal(20), "img2Base64"));
        basket.add(new Dish(3L, "name3", DishType.SOUP, new BigDecimal(30), "img3Base64"));

        BigDecimal orderCost = basket.stream().map(Dish::getCost).reduce(BigDecimal::add).get();

        when(requestDataHolderMock.getSessionAttribute(BASKET_ATTR)).thenReturn(basket);

        CommandResult expectedResult = new CommandResult(MAKE_ORDER_PAGE, true);
        //when
        CommandResult result = makeOrderCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(BASKET_ATTR);
        verify(requestDataHolderMock).putSessionAttribute(ORDER_COST_ATTR, orderCost);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, MAKE_ORDER_PAGE);

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("parameterTestExecuteShouldReturnCommandResultWithForwardToMakeCurrentPageWhenBasketIsEmptyOrNullProvider")
    void testExecuteShouldReturnCommandResultWithForwardToMakeCurrentPageWhenBasketIsEmptyOrNull(List<Dish> basket) throws ServiceException {
        //given
        String currentPageParam = "wwwTestCom";

        when(requestDataHolderMock.getSessionAttribute(BASKET_ATTR)).thenReturn(basket);
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_PARAM)).thenReturn(currentPageParam);

        CommandResult expectedResult = new CommandResult(currentPageParam, false);
        //when
        CommandResult result = makeOrderCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(BASKET_ATTR);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE);
        verify(requestDataHolderMock).getSessionAttribute(CURRENT_PAGE_PARAM);

        assertEquals(expectedResult, result);

    }

    @DataProvider
    public static Object[][] parameterTestExecuteShouldReturnCommandResultWithForwardToMakeCurrentPageWhenBasketIsEmptyOrNullProvider() {
        return new Object[][]{
                {new ArrayList<>()},
                {null}
        };
    }


}