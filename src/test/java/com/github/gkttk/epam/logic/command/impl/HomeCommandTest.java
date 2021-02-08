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

public class HomeCommandTest {

    private final static String HOME_PAGE = "index.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    private Command homeCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.homeCommand = new HomeCommand();
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToHomePage() throws ServiceException {
        //given
        CommandResult expectedResult = new CommandResult(HOME_PAGE, false);
        //when
        CommandResult result = homeCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, HOME_PAGE);
        assertEquals(expectedResult, result);
    }

}