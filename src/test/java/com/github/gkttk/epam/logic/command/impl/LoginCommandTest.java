package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginCommandTest {

    private final static String USER_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String START_PAGE = "index.jsp";
    private final static String ERROR_MESSAGE = "error.message.credentials";
    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String DISHES_ATTR = "dishes";
    private final static String CURRENT_PAGE_ATTR = "currentPage";
    private final static String MESSAGE_ATTR = "message";
    private final static String ERROR_MESSAGE_BLOCKED = "error.message.blocked";

    private final static User TEST_USER = new User(1L, "testLogin"/*, "testPassword"*/, UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private final static User BLOCKED_TEST_USER = new User(1L, "testLogin"/*, "testPassword"*/, UserRole.USER,
            50, new BigDecimal(25), true, "imgBase64Test");

    private UserService userServiceMock;
    private DishService dishServiceMock;

    private Command loginCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);
        this.dishServiceMock = Mockito.mock(DishService.class);
        this.loginCommand = new LoginCommand(userServiceMock, dishServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToStartPageWhenUserDoesNotInDb() throws ServiceException {
        //given
        String login = TEST_USER.getLogin();
        String password = "password";

        when(requestDataHolderMock.getRequestParameter(LOGIN_PARAM)).thenReturn(login);
        when(requestDataHolderMock.getRequestParameter(PASSWORD_PARAM)).thenReturn(password);
        when(userServiceMock.login(login, password)).thenReturn(Optional.empty());

        CommandResult expectedResult = new CommandResult(START_PAGE, false);
        //when
        CommandResult result = loginCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LOGIN_PARAM);
        verify(requestDataHolderMock).getRequestParameter(PASSWORD_PARAM);
        verify(userServiceMock).login(login, password);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToUserPageWhenUserExistsInDb() throws ServiceException {
        //given
        String login = TEST_USER.getLogin();
        String password = "password";
        List<Dish> dishes = Arrays.asList(null, null, null);

        when(requestDataHolderMock.getRequestParameter(LOGIN_PARAM)).thenReturn(login);
        when(requestDataHolderMock.getRequestParameter(PASSWORD_PARAM)).thenReturn(password);
        when(userServiceMock.login(login, password)).thenReturn(Optional.of(TEST_USER));
        when(dishServiceMock.getAllEnabled()).thenReturn(dishes);

        CommandResult expectedResult = new CommandResult(USER_PAGE, true);
        //when
        CommandResult result = loginCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LOGIN_PARAM);
        verify(requestDataHolderMock).getRequestParameter(PASSWORD_PARAM);
        verify(userServiceMock).login(login, password);
        verify(requestDataHolderMock).putSessionAttribute(AUTH_USER_ATTR, TEST_USER);
        verify(dishServiceMock).getAllEnabled();
        verify(requestDataHolderMock).putSessionAttribute(DISHES_ATTR, dishes);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_ATTR, USER_PAGE);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToStartPageWhenUserIsBlocked() throws ServiceException {
        //given
        String login = TEST_USER.getLogin();
        String password = "password";

        when(requestDataHolderMock.getRequestParameter(LOGIN_PARAM)).thenReturn(login);
        when(requestDataHolderMock.getRequestParameter(PASSWORD_PARAM)).thenReturn(password);
        when(userServiceMock.login(login, password)).thenReturn(Optional.of(BLOCKED_TEST_USER));

        CommandResult expectedResult = new CommandResult(START_PAGE, false);
        //when
        CommandResult result = loginCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LOGIN_PARAM);
        verify(requestDataHolderMock).getRequestParameter(PASSWORD_PARAM);
        verify(userServiceMock).login(login, password);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_BLOCKED);

        assertEquals(expectedResult, result);
    }

}