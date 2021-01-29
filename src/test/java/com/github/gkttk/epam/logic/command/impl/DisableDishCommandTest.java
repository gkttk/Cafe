package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DisableDishCommandTest {

    private final static String DISH_ID_PARAM = "dishId";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String DISHES_ATTR = "dishes";

    private DishService dishServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command removeDishCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.dishServiceMock = Mockito.mock(DishService.class);
        this.removeDishCommand = new RemoveDishCommand(dishServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMenuPage() throws ServiceException {
        //given
        String dishIdParam = "1";
        long dishId = Long.parseLong(dishIdParam);
        List<Dish> dishes = Arrays.asList(null, null, null);

        when(requestDataHolderMock.getRequestParameter(DISH_ID_PARAM)).thenReturn(dishIdParam);
        when(dishServiceMock.getAllEnabled()).thenReturn(dishes);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, true);
        //when
        CommandResult result = removeDishCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DISH_ID_PARAM);
        verify(dishServiceMock).disableDish(dishId);
        verify(dishServiceMock).getAllEnabled();
        verify(requestDataHolderMock).putSessionAttribute(DISHES_ATTR, dishes);

        assertEquals(expectedResult, result);
    }


}