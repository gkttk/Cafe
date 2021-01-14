package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersPageCommandTest {

    private final static String USERS_ATTR = "users";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";

    private UserService userServiceMock;

    private Command usersPageCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);
        this.usersPageCommand = new UsersPageCommand(userServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToUsersPage() throws ServiceException {
        //given
        List<UserInfo> users = Arrays.asList(null, null, null);

        when(userServiceMock.getAll()).thenReturn(users);

        CommandResult expectedResult = new CommandResult(USERS_PAGE, true);
        //when
        CommandResult result = usersPageCommand.execute(requestDataHolderMock);
        //then
        verify(userServiceMock).getAll();
        verify(requestDataHolderMock).putSessionAttribute(USERS_ATTR, users);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, USERS_PAGE);

        assertEquals(expectedResult, result);
    }


}