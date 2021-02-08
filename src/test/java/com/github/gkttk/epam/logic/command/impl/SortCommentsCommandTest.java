package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.enums.CommentSortType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SortCommentsCommandTest {

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String PAGE_NUMBER_PARAM = "pageNumber";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String SORT_TYPE_PARAM = "sortType";

    private CommentService commentServiceMock;

    private Command sortCommentsCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.commentServiceMock = Mockito.mock(CommentService.class);
        this.sortCommentsCommand = new SortCommentsCommand(commentServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCommentsPage() throws ServiceException {
        //given
        String currentPageParam = "1";
        int currentPage = Integer.parseInt(currentPageParam);
        long dishId = 2L;
        String sortTypeParam = CommentSortType.RATING.name();
        CommentSortType sortType = CommentSortType.valueOf(sortTypeParam);
        List<CommentInfo> comments = Arrays.asList(null, null, null);

        CommandResult expectedResult = new CommandResult(COMMENTS_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(PAGE_NUMBER_PARAM)).thenReturn(currentPageParam);
        when(requestDataHolderMock.getSessionAttribute(DISH_ID_ATTR)).thenReturn(dishId);
        when(requestDataHolderMock.getRequestParameter(SORT_TYPE_PARAM)).thenReturn(sortTypeParam);
        when(commentServiceMock.getAllByDishIdPagination(dishId, currentPage, sortType)).thenReturn(comments);
        //when
        CommandResult result = sortCommentsCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(PAGE_NUMBER_PARAM);
        verify(requestDataHolderMock).getSessionAttribute(DISH_ID_ATTR);
        verify(requestDataHolderMock).getRequestParameter(SORT_TYPE_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(SORT_TYPE_PARAM, sortType);
        verify(commentServiceMock).getAllByDishIdPagination(dishId, currentPage, sortType);
        verify(requestDataHolderMock).putSessionAttribute(COMMENTS_ATTR, comments);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, COMMENTS_PAGE);

        assertEquals(expectedResult, result);
    }


}