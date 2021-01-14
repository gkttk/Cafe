package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChangeAvatarCommandTest {

    private final static String FILE_ATTR = "file";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String MAIN_PAGE_ATTR = "index.jsp";
    private final static String CURRENT_PAGE_ATTR = "currentPage";

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private UserService userServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command changeAvatarCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.userServiceMock = Mockito.mock(UserService.class);

        this.changeAvatarCommand = new ChangeAvatarCommand(userServiceMock);
    }

    @ParameterizedTest
    @MethodSource("parameterTestExecuteShouldReturnCommandResultWithRedirectPageProvider")
    void testExecuteShouldReturnCommandResultWithRedirectPage(String redirectPage, CommandResult expectedResult)
            throws ServiceException, IOException {
        //given
        Part partMock = Mockito.mock(Part.class);
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);
        long userId = TEST_USER.getId();

        when(requestDataHolderMock.getRequestAttribute(FILE_ATTR)).thenReturn(partMock);
        when(partMock.getInputStream()).thenReturn(inputStream);
        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(userServiceMock.getById(userId)).thenReturn(Optional.of(TEST_USER));
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_ATTR)).thenReturn(redirectPage);

        //when
        CommandResult result = changeAvatarCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestAttribute(FILE_ATTR);
        verify(partMock).getInputStream();
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(userServiceMock).changeAvatar(TEST_USER, null);
        verify(userServiceMock).getById(userId);
        verify(requestDataHolderMock).putSessionAttribute(AUTH_USER_ATTR, TEST_USER);
        verify(requestDataHolderMock).getSessionAttribute(CURRENT_PAGE_ATTR);

        assertEquals(expectedResult, result);
    }

    @DataProvider
    public static Object[][] parameterTestExecuteShouldReturnCommandResultWithRedirectPageProvider() {
        return new Object[][]{
                {null, new CommandResult(MAIN_PAGE_ATTR, true)},
                {"wwwTestCom", new CommandResult("wwwTestCom", true)}
        };
    }

    @Test
    void testExecuteShouldThrowExceptionWhenCantGetInputStreamFromPart() throws IOException {
        //given
        Part partMock = Mockito.mock(Part.class);
        when(requestDataHolderMock.getRequestAttribute(FILE_ATTR)).thenReturn(partMock);
        when(partMock.getInputStream()).thenThrow(new IOException());
        //when
        //then
        assertThrows(ServiceException.class, () -> changeAvatarCommand.execute(requestDataHolderMock));
        verify(requestDataHolderMock).getRequestAttribute(FILE_ATTR);
        verify(partMock).getInputStream();
    }


}