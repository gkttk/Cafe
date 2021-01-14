package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SortUsersCommandTest {

    private final static String USER_STATUS_PARAM = "userStatus";
    private final static String USERS_ATTR = "users";
    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";

    private UserService userServiceMock;

    private Command sortUsersCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);
        this.sortUsersCommand = new SortUsersCommand(userServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToUsersPageWhenUserStatusParamIsNotNull() throws ServiceException {
        //given
        String userStatusParam = UserStatus.BLOCKED.name();
        UserStatus userStatus = UserStatus.valueOf(userStatusParam);
        List<UserInfo> users = Arrays.asList(null, null, null);

        CommandResult expectedResult = new CommandResult(USERS_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(USER_STATUS_PARAM)).thenReturn(userStatusParam);
        when(userServiceMock.getByStatus(userStatus)).thenReturn(users);
        //when
        CommandResult result = sortUsersCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(USER_STATUS_PARAM);
        verify(userServiceMock).getByStatus(userStatus);
        verify(requestDataHolderMock).putSessionAttribute(USERS_ATTR, users);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToUsersPageWhenUserStatusParamIsNull() throws ServiceException {
        //given
        String userStatusParam = null;
        List<UserInfo> users = Arrays.asList(null, null, null);

        CommandResult expectedResult = new CommandResult(USERS_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(USER_STATUS_PARAM)).thenReturn(userStatusParam);
        when(userServiceMock.getAll()).thenReturn(users);
        //when
        CommandResult result = sortUsersCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(USER_STATUS_PARAM);
        verify(userServiceMock).getAll();
        verify(requestDataHolderMock).putSessionAttribute(USERS_ATTR, users);

        assertEquals(expectedResult, result);
    }


}