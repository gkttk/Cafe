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

public class ChangePointsCommandTest {

    private final static String USER_ID_PARAM = "userId";
    private final static String POINTS_PARAM = "points";
    private final static String IS_ADD_PARAM = "isAdd";
    private final static String USERS_ATTR = "users";
    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";

    private UserService userServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command changeUserPointCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);

        this.changeUserPointCommand = new ChangeUserPointsCommand(userServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToUserPage() throws ServiceException {
        //given
        String userIdParam = "1";
        long userId = Long.parseLong(userIdParam);
        String pointsParam = "50";
        int points = Integer.parseInt(pointsParam);
        String isAddParam = "true";
        boolean isAdd = Boolean.parseBoolean(isAddParam);

        List<UserInfo> users = Arrays.asList(null, null, null);

        when(requestDataHolderMock.getRequestParameter(USER_ID_PARAM)).thenReturn(userIdParam);
        when(requestDataHolderMock.getRequestParameter(POINTS_PARAM)).thenReturn(pointsParam);
        when(requestDataHolderMock.getRequestParameter(IS_ADD_PARAM)).thenReturn(isAddParam);
        when(userServiceMock.getAll()).thenReturn(users);

        CommandResult expectedResult = new CommandResult(USERS_PAGE, true);
        //when
        CommandResult result = changeUserPointCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(USER_ID_PARAM);
        verify(requestDataHolderMock).getRequestParameter(POINTS_PARAM);
        verify(requestDataHolderMock).getRequestParameter(IS_ADD_PARAM);
        verify(userServiceMock).changePoints(userId, points, isAdd);
        verify(userServiceMock).getAll();
        verify(requestDataHolderMock).putSessionAttribute(USERS_ATTR, users);

        assertEquals(expectedResult, result);
    }


}