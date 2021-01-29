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

public class MenuPageCommandTest {

    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    private Command menuPageCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.menuPageCommand = new MenuPageCommand();
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToMenuPage() throws ServiceException {
        //given
        CommandResult expectedResult = new CommandResult(MENU_PAGE, false);
        //when
        CommandResult result = menuPageCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, MENU_PAGE);

        assertEquals(expectedResult, result);
    }


}