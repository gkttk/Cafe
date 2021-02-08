package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChangeUserStatusCommandTest {

    private final static String USER_ID_PARAM = "userId";
    private final static String IS_BLOCKED_PARAM = "blocked";
    private final static String USERS_ATTR = "users";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    private final static UserInfo TEST_USER_INFO = new UserInfo(1L, "testLogin", UserRole.USER,
            50, false);

    private UserService userServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command changeUserStatusCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);
        this.changeUserStatusCommand = new ChangeUserStatusCommand(userServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCurrentPage() throws ServiceException {
        //given
        String userIdParam = "1";
        long userId = Long.parseLong(userIdParam);

        String isBlockedParam = "true";
        boolean isBlocked = Boolean.parseBoolean(isBlockedParam);

        UserInfo firstUserInfo = new UserInfo(2L, "testLogin2", UserRole.USER, 52, false);
        UserInfo secondUserInfo = new UserInfo(3L, "testLogin3", UserRole.USER, 53, false);

        List<UserInfo> users = new ArrayList<>();
        users.add(firstUserInfo);
        users.add(secondUserInfo);
        users.add(TEST_USER_INFO);

        UserInfo changedUserInfo = new UserInfo(1L, "testLogin", UserRole.USER,
                50, false);

        List<UserInfo> changedUsers = new ArrayList<>();
        changedUsers.add(firstUserInfo);
        changedUsers.add(secondUserInfo);
        changedUsers.add(changedUserInfo);

        String redirectPage = "wwwTestCom";

        when(requestDataHolderMock.getRequestParameter(USER_ID_PARAM)).thenReturn(userIdParam);
        when(requestDataHolderMock.getRequestParameter(IS_BLOCKED_PARAM)).thenReturn(isBlockedParam);
        when(userServiceMock.changeUserStatus(userId, isBlocked)).thenReturn(Optional.of(TEST_USER_INFO));
        when(requestDataHolderMock.getSessionAttribute(USERS_ATTR)).thenReturn(users);
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_PARAM)).thenReturn(redirectPage);

        CommandResult expectedResult = new CommandResult(redirectPage, true);
        //when
        CommandResult result = changeUserStatusCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(USER_ID_PARAM);
        verify(requestDataHolderMock).getRequestParameter(IS_BLOCKED_PARAM);
        verify(userServiceMock).changeUserStatus(userId, isBlocked);
        verify(requestDataHolderMock).getSessionAttribute(USERS_ATTR);
        verify(requestDataHolderMock).putSessionAttribute(USERS_ATTR, changedUsers);
        verify(requestDataHolderMock).getSessionAttribute(CURRENT_PAGE_PARAM);

        assertEquals(expectedResult, result);
    }


}