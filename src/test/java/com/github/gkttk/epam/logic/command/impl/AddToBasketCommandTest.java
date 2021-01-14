package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddToBasketCommandTest {

    private final static String BASKET_ATTR = "basket";
    private final static String DISH_ID_PARAM = "dishId";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    private DishService dishServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command addToBasketCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.dishServiceMock = Mockito.mock(DishService.class);

        this.addToBasketCommand = new AddToBasketCommand(dishServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMenuPageWhenDataIsValid() throws ServiceException {
        //given
        ArrayList<Dish> basket = new ArrayList<>();
        basket.add(null);
        basket.add(null);
        String dishIdParam = "2";
        long dishId = Long.parseLong(dishIdParam);

        Dish dish = new Dish(dishId, "name", DishTypes.SALAD, new BigDecimal(2), "imgBase64");

        List<Dish> newBasket = new ArrayList<>(basket);
        newBasket.add(dish);

        when(requestDataHolderMock.getSessionAttribute(BASKET_ATTR)).thenReturn(basket);
        when(requestDataHolderMock.getRequestParameter(DISH_ID_PARAM)).thenReturn(dishIdParam);
        when(dishServiceMock.getDishById(dishId)).thenReturn(Optional.of(dish));

        CommandResult expectedResult = new CommandResult(MENU_PAGE, true);
        //when
        CommandResult result = addToBasketCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(BASKET_ATTR);
        verify(requestDataHolderMock).getRequestParameter(DISH_ID_PARAM);
        verify(dishServiceMock).getDishById(dishId);
        verify(requestDataHolderMock).putSessionAttribute(BASKET_ATTR, newBasket);

        assertEquals(expectedResult, result);
    }


}