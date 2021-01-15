package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.validator.UserLoginValidator;
import com.github.gkttk.epam.logic.validator.UserPasswordValidator;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegistrationCommandTest {

    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String START_PAGE = "index.jsp";
    private final static String MESSAGE_ATTR = "message";
    private final static String ERROR_MESSAGE = "error.message.registration";
    private final static String REGISTRATION_PAGE = "/WEB-INF/view/registration_page.jsp";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String SUCCESS_MSG = "success.message.registration";

    private UserService userServiceMock;
    private Validator userLoginValidatorMock;
    private Validator userPasswordValidatorMock;

    private Command registrationCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);
        this.userLoginValidatorMock = Mockito.mock(UserLoginValidator.class);
        this.userPasswordValidatorMock = Mockito.mock(UserPasswordValidator.class);
        this.registrationCommand = new RegistrationCommand(userServiceMock, userLoginValidatorMock, userPasswordValidatorMock);

        when(requestDataHolderMock.getRequestParameter(LOGIN_PARAM)).thenReturn(LOGIN);
        when(requestDataHolderMock.getRequestParameter(PASSWORD_PARAM)).thenReturn(PASSWORD);

    }


    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToStartPageIfUserWasRegistered() throws ServiceException {
        //given
        when(userLoginValidatorMock.validate(LOGIN)).thenReturn(true);
        when(userPasswordValidatorMock.validate(PASSWORD)).thenReturn(true);
        when(userServiceMock.registration(LOGIN, PASSWORD)).thenReturn(true);

        CommandResult expectedResult = new CommandResult(START_PAGE, true);
        //when
        CommandResult result = registrationCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LOGIN_PARAM);
        verify(userLoginValidatorMock).validate(LOGIN);
        verify(requestDataHolderMock).getRequestParameter(PASSWORD_PARAM);
        verify(userPasswordValidatorMock).validate(PASSWORD);
        verify(userServiceMock).registration(LOGIN, PASSWORD);
        verify(requestDataHolderMock).putSessionAttribute(MESSAGE_ATTR, SUCCESS_MSG);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToStartPageIfUserWasNotRegistered() throws ServiceException {
        //given
        when(userLoginValidatorMock.validate(LOGIN)).thenReturn(true);
        when(userPasswordValidatorMock.validate(PASSWORD)).thenReturn(true);
        when(userServiceMock.registration(LOGIN, PASSWORD)).thenReturn(false);

        CommandResult expectedResult = new CommandResult(REGISTRATION_PAGE, false);
        //when
        CommandResult result = registrationCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LOGIN_PARAM);
        verify(userLoginValidatorMock).validate(LOGIN);
        verify(requestDataHolderMock).getRequestParameter(PASSWORD_PARAM);
        verify(userPasswordValidatorMock).validate(PASSWORD);
        verify(userServiceMock).registration(LOGIN, PASSWORD);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToStartPageIfLoginIsNotValid() throws ServiceException {
        //given
        when(userLoginValidatorMock.validate(LOGIN)).thenReturn(false);
        when(userPasswordValidatorMock.validate(PASSWORD)).thenReturn(true);

        CommandResult expectedResult = new CommandResult(REGISTRATION_PAGE, false);
        //when
        CommandResult result = registrationCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LOGIN_PARAM);
        verify(userLoginValidatorMock).validate(LOGIN);
        verify(requestDataHolderMock).getRequestParameter(PASSWORD_PARAM);
        verify(userPasswordValidatorMock).validate(PASSWORD);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToStartPageIfPasswordIsNotValid() throws ServiceException {
        //given
        when(userLoginValidatorMock.validate(LOGIN)).thenReturn(true);
        when(userPasswordValidatorMock.validate(PASSWORD)).thenReturn(false);

        CommandResult expectedResult = new CommandResult(REGISTRATION_PAGE, false);
        //when
        CommandResult result = registrationCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LOGIN_PARAM);
        verify(userLoginValidatorMock).validate(LOGIN);
        verify(requestDataHolderMock).getRequestParameter(PASSWORD_PARAM);
        verify(userPasswordValidatorMock).validate(PASSWORD);
        verify(requestDataHolderMock).putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE);

        assertEquals(expectedResult, result);
    }


}