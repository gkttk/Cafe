package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SortDishesCommandTest {

    private final static String DISH_TYPE_PARAM = "dishType";
    private final static String DISHES_ATTR = "dishes";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    private DishService dishServiceMock;

    private Command sortDishesCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.dishServiceMock = Mockito.mock(DishService.class);
        this.sortDishesCommand = new SortDishesCommand(dishServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMenuPageWhenDishTypeParamIsNotNull() throws ServiceException {
        //given
        String dishTypeParam = DishType.SALAD.name();
        DishType dishType = DishType.valueOf(dishTypeParam);
        List<Dish> dishes = Arrays.asList(null, null, null);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(DISH_TYPE_PARAM)).thenReturn(dishTypeParam);
        when(dishServiceMock.getByType(dishType)).thenReturn(dishes);
        //when
        CommandResult result = sortDishesCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DISH_TYPE_PARAM);
        verify(dishServiceMock).getByType(dishType);
        verify(requestDataHolderMock).putSessionAttribute(DISHES_ATTR, dishes);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMenuPageWhenDishTypeParamIsNull() throws ServiceException {
        //given
        String dishTypeParam = null;
        List<Dish> dishes = Arrays.asList(null, null, null, null);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(DISH_TYPE_PARAM)).thenReturn(dishTypeParam);
        when(dishServiceMock.getAllEnabled()).thenReturn(dishes);
        //when
        CommandResult result = sortDishesCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DISH_TYPE_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(DISHES_ATTR, dishes);

        assertEquals(expectedResult, result);
    }


}