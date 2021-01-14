package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CancelDishCommandTest {

    private final static String BASKET_ATTR = "basket";
    private final static String DISH_ID_PARAM = "dishId";
    private final static String ORDER_COST_ATTR = "orderCost";
    private final static String CURRENT_PAGE_ATTR = "currentPage";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";


    private RequestDataHolder requestDataHolderMock;

    private Command cancelDishCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.cancelDishCommand = new CancelDishCommand();
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMenuPageWhenBasketBecomeEmpty() throws ServiceException {
        //given
        Dish cancelledDish = new Dish(2L, "name2", DishTypes.BEVERAGE, new BigDecimal(3), "imgBase645");
        ArrayList<Dish> basket = new ArrayList<>();
        basket.add(cancelledDish);

        String orderCostAttr = "2";
        BigDecimal orderCost = new BigDecimal(orderCostAttr);
        BigDecimal cancelledDishCost = cancelledDish.getCost();
        BigDecimal newOrderCost = orderCost.subtract(cancelledDishCost);

        String cancelledDishIdParam = String.valueOf(cancelledDish.getId());
        long cancelledDishId = Long.parseLong(cancelledDishIdParam);

        ArrayList<Dish> newBasket = new ArrayList<>(basket);
        newBasket.removeIf(dish -> dish.getId().equals(cancelledDishId));

        when(requestDataHolderMock.getSessionAttribute(BASKET_ATTR)).thenReturn(basket);
        when(requestDataHolderMock.getRequestParameter(DISH_ID_PARAM)).thenReturn(cancelledDishIdParam);
        when(requestDataHolderMock.getSessionAttribute(ORDER_COST_ATTR)).thenReturn(orderCost);
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_ATTR)).thenReturn(MENU_PAGE);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, true);
        //when
        CommandResult result = cancelDishCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(BASKET_ATTR);
        verify(requestDataHolderMock).getRequestParameter(DISH_ID_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(BASKET_ATTR, newBasket);
        verify(requestDataHolderMock).getSessionAttribute(ORDER_COST_ATTR);
        verify(requestDataHolderMock).putSessionAttribute(ORDER_COST_ATTR, newOrderCost);


        assertEquals(expectedResult, result);
    }


    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCurrentPage() throws ServiceException {
        //given
        Dish cancelledDish = new Dish(2L, "name2", DishTypes.BEVERAGE, new BigDecimal(3), "imgBase645");
        ArrayList<Dish> basket = new ArrayList<>();
        basket.add(new Dish(1L, "name1", DishTypes.SALAD, new BigDecimal(2), "imgBase64"));
        basket.add(cancelledDish);
        basket.add(new Dish(3L, "name3", DishTypes.SOUP, new BigDecimal(4), "imgBase642"));

        String orderCostAttr = "10";
        BigDecimal orderCost = new BigDecimal(orderCostAttr);
        BigDecimal cancelledDishCost = cancelledDish.getCost();
        BigDecimal newOrderCost = orderCost.subtract(cancelledDishCost);

        String cancelledDishIdParam = String.valueOf(cancelledDish.getId());
        long cancelledDishId = Long.parseLong(cancelledDishIdParam);

        ArrayList<Dish> newBasket = new ArrayList<>(basket);
        newBasket.removeIf(dish -> dish.getId().equals(cancelledDishId));

        String redirectPage = "wwwTestCom";

        when(requestDataHolderMock.getSessionAttribute(BASKET_ATTR)).thenReturn(basket);
        when(requestDataHolderMock.getRequestParameter(DISH_ID_PARAM)).thenReturn(cancelledDishIdParam);
        when(requestDataHolderMock.getSessionAttribute(ORDER_COST_ATTR)).thenReturn(orderCost);
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_ATTR)).thenReturn(redirectPage);

        CommandResult expectedResult = new CommandResult(redirectPage, true);
        //when
        CommandResult result = cancelDishCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(BASKET_ATTR);
        verify(requestDataHolderMock).getRequestParameter(DISH_ID_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(BASKET_ATTR, newBasket);
        verify(requestDataHolderMock).getSessionAttribute(ORDER_COST_ATTR);
        verify(requestDataHolderMock).putSessionAttribute(ORDER_COST_ATTR, newOrderCost);
        verify(requestDataHolderMock).getSessionAttribute(CURRENT_PAGE_ATTR);

        assertEquals(expectedResult, result);
    }


}