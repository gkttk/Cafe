package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocaleCommandTest {

    private final static String START_PAGE = "index.jsp";
    private final static String LANGUAGE_PARAM = "lang";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String LOCALE_ATTR = "locale";

    private Command localeCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.localeCommand = new LocaleCommand();
    }

    @ParameterizedTest
    @MethodSource("parameterTestExecuteShouldReturnCommandResultWithRedirectToCurrentPageProvider")
    void testExecuteShouldReturnCommandResultWithRedirectToCurrentPage(String currentPageParam, String redirectPage) throws ServiceException {
        //given
        String languageParam = "by";
        CommandResult expectedResult = new CommandResult(redirectPage, true);

        when(requestDataHolderMock.getRequestParameter(LANGUAGE_PARAM)).thenReturn(languageParam);
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_PARAM)).thenReturn(currentPageParam);
        //when
        CommandResult result = localeCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(LANGUAGE_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(LOCALE_ATTR, languageParam);
        verify(requestDataHolderMock).getSessionAttribute(CURRENT_PAGE_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, redirectPage);

        assertEquals(expectedResult, result);
    }

    @DataProvider
    public static Object[][] parameterTestExecuteShouldReturnCommandResultWithRedirectToCurrentPageProvider() {
        return new Object[][]{
                {null, START_PAGE},
                {"wwwTestCom", "wwwTestCom"}
        };
    }

}