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

public class RegistrationGetPageCommandTest {

    private final static String REGISTRATION_PAGE = "/WEB-INF/view/registration_page.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    private Command registrationGetPageCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.registrationGetPageCommand = new RegistrationGetPageCommand();
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToRegistrationPage() throws ServiceException {
        //given
        CommandResult expectedResult = new CommandResult(REGISTRATION_PAGE, false);
        //when
        CommandResult result = registrationGetPageCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, REGISTRATION_PAGE);

        assertEquals(expectedResult, result);
    }

}