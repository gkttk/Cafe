package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddMoneyCommandTest {

    private final static String AUTH_USER_ATTR = "authUser";
    private final static String MONEY_PARAM = "money";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    private final static User TEST_USER = new User(1L, "testLogin", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private UserService userServiceMock;
    private Validator moneyValidatorMock;

    private RequestDataHolder requestDataHolderMock;

    private Command addMoneyCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);
        this.moneyValidatorMock = Mockito.mock(Validator.class);

        this.addMoneyCommand = new AddMoneyCommand(userServiceMock, moneyValidatorMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCurrentPageWhenDataIsValid() throws ServiceException, IOException {
        //given
        String moneyParam = "10";
        BigDecimal money = new BigDecimal(moneyParam);
        long userId = TEST_USER.getId();
        String redirectPage = "wwwTestCom";

        when(requestDataHolderMock.getRequestParameter(MONEY_PARAM)).thenReturn(moneyParam);
        when(moneyValidatorMock.validate(moneyParam)).thenReturn(true);
        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(userServiceMock.getById(userId)).thenReturn(Optional.of(TEST_USER));
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_PARAM)).thenReturn(redirectPage);

        CommandResult expectedResult = new CommandResult(redirectPage, true);
        //when
        CommandResult result = addMoneyCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(MONEY_PARAM);
        verify(moneyValidatorMock).validate(moneyParam);
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(userServiceMock).addMoney(userId, money);
        verify(userServiceMock).getById(userId);
        verify(requestDataHolderMock).putSessionAttribute(AUTH_USER_ATTR, TEST_USER);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToCurrentPageWhenMoneyIsNotValid() throws ServiceException {
        //given
        String incorrectMoneyParam = "-5";
        String currentPageParam = "wwwTestCom";

        when(requestDataHolderMock.getRequestParameter(MONEY_PARAM)).thenReturn(incorrectMoneyParam);
        when(moneyValidatorMock.validate(incorrectMoneyParam)).thenReturn(false);
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_PARAM)).thenReturn(currentPageParam);

        CommandResult expectedResult = new CommandResult(currentPageParam, false);
        //when
        CommandResult result = addMoneyCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(MONEY_PARAM);
        verify(moneyValidatorMock).validate(incorrectMoneyParam);
        verify(requestDataHolderMock).getSessionAttribute(CURRENT_PAGE_PARAM);
        assertEquals(expectedResult, result);
    }


}