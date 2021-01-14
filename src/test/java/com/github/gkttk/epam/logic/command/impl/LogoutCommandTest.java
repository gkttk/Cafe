package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class LogoutCommandTest {

    private final static String START_PAGE = "index.jsp";

    private Command logoutCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.logoutCommand = new LogoutCommand();
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToStartPage() throws ServiceException {
        //given
        CommandResult expectedResult = new CommandResult(START_PAGE, true);
        //when
        CommandResult result = logoutCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).invalidateSession();

        assertEquals(expectedResult, result);
    }

}