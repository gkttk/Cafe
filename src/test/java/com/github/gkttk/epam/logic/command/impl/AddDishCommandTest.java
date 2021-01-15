package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.logic.validator.DishCostValidator;
import com.github.gkttk.epam.logic.validator.DishNameValidator;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddDishCommandTest {

    private final static String MESSAGE_ATTR = "message";
    private final static String ERROR_MESSAGE_DISH_NAME = "error.message.dish.name";
    private final static String ERROR_MESSAGE_DISH_COST = "error.message.dish.cost";
    private final static String FILE_ATTR = "file";
    private final static String DISH_NAME_PARAM = "name";
    private final static String DISH_COST_PARAM = "cost";
    private final static String DISH_TYPE_PARAM = "type";
    private final static String DISHES_ATTR = "dishes";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    private DishService dishServiceMock;
    private Validator dishNameValidatorMock;
    private Validator dishCostValidatorMock;

    private RequestDataHolder requestDataHolderMock;

    private Command addDishCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.dishServiceMock = Mockito.mock(DishService.class);
        this.dishNameValidatorMock = Mockito.mock(DishNameValidator.class);
        this.dishCostValidatorMock = Mockito.mock(DishCostValidator.class);

        this.addDishCommand = new AddDishCommand(dishServiceMock, dishNameValidatorMock, dishCostValidatorMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToMenuPageWhenDataIsValid() throws ServiceException, IOException {
        //given
        String dishName = "DishName";
        String dishCostParam = "1.0";
        BigDecimal dishCost = new BigDecimal(dishCostParam);
        String typeParam = DishTypes.SALAD.name();
        DishTypes dishType = DishTypes.valueOf(typeParam);
        Part partMock = Mockito.mock(Part.class);
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);
        List<Dish> dishes = Arrays.asList(null, null);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(DISH_NAME_PARAM)).thenReturn(dishName);
        when(dishNameValidatorMock.validate(dishName)).thenReturn(true);
        when(requestDataHolderMock.getRequestParameter(DISH_COST_PARAM)).thenReturn(dishCostParam);
        when(dishCostValidatorMock.validate(dishCostParam)).thenReturn(true);
        when(requestDataHolderMock.getRequestParameter(DISH_TYPE_PARAM)).thenReturn(typeParam);
        when(requestDataHolderMock.getRequestAttribute(FILE_ATTR)).thenReturn(partMock);
        when(partMock.getInputStream()).thenReturn(inputStream);
        when(dishServiceMock.getAllEnabled()).thenReturn(dishes);

        //when
        CommandResult result = addDishCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DISH_NAME_PARAM);
        verify(dishNameValidatorMock).validate(dishName);
        verify(requestDataHolderMock).getRequestParameter(DISH_COST_PARAM);
        verify(dishCostValidatorMock).validate(dishCostParam);
        verify(requestDataHolderMock).getRequestParameter(DISH_TYPE_PARAM);
        verify(requestDataHolderMock).getRequestAttribute(FILE_ATTR);
        verify(partMock).getInputStream();
        verify(dishServiceMock).addDish(dishName, dishCost, dishType, null);
        verify(dishServiceMock).getAllEnabled();
        verify(requestDataHolderMock).putSessionAttribute(DISHES_ATTR, dishes);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToMenuPageWhenDishNameIsNotValid() throws ServiceException {
        //given
        String incorrectDishName = "1234";

        when(requestDataHolderMock.getRequestParameter(DISH_NAME_PARAM)).thenReturn(incorrectDishName);
        when(dishNameValidatorMock.validate(incorrectDishName)).thenReturn(false);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, false);
        //when
        CommandResult result = addDishCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DISH_NAME_PARAM);
        verify(dishNameValidatorMock).validate(incorrectDishName);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_DISH_NAME);
        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToMenuPageWhenDishCostIsNotValid() throws ServiceException {
        //given
        String dishName = "DishName";
        String incorrectDishCostParam = "1.0";

        when(requestDataHolderMock.getRequestParameter(DISH_NAME_PARAM)).thenReturn(dishName);
        when(dishNameValidatorMock.validate(dishName)).thenReturn(true);
        when(requestDataHolderMock.getRequestParameter(DISH_COST_PARAM)).thenReturn(incorrectDishCostParam);
        when(dishCostValidatorMock.validate(incorrectDishCostParam)).thenReturn(false);

        CommandResult expectedResult = new CommandResult(MENU_PAGE, false);
        //when
        CommandResult result = addDishCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(DISH_NAME_PARAM);
        verify(dishNameValidatorMock).validate(dishName);
        verify(requestDataHolderMock).getRequestParameter(DISH_COST_PARAM);
        verify(dishCostValidatorMock).validate(incorrectDishCostParam);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_DISH_COST);

        assertEquals(expectedResult, result);
    }
}