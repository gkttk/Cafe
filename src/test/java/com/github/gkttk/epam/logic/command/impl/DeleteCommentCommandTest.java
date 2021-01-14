package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.enums.CommentSortTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteCommentCommandTest {

    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String PAGE_COUNT_ATTR = "pageCount";
    private final static String CURRENT_PAGE_PAGINATION_ATTR = "currentPagePagination";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String SORT_TYPE_ATTR = "sortType";

    private CommentService commentServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command deleteCommentCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.commentServiceMock = Mockito.mock(CommentService.class);
        this.deleteCommentCommand = new DeleteCommentCommand(commentServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCommentsPage() throws ServiceException {
        //given
        String commentIdParam = "1";
        long commentId = Long.parseLong(commentIdParam);

        List<CommentInfo> commentInfos = new ArrayList<>();
        commentInfos.add(new CommentInfo(1L, "text1", 10, LocalDateTime.MIN, "login1",
                "imgBase641"));
        commentInfos.add(new CommentInfo(2L, "text2", 20, LocalDateTime.MIN, "login2",
                "imgBase642"));
        commentInfos.add(new CommentInfo(3L, "text3", 30, LocalDateTime.MIN, "login3",
                "imgBase643"));

        long dishId = 5L;
        int pageCount = 3;
        int currentPageAttr = 1;
        CommentSortTypes type = CommentSortTypes.DATE;

        List<CommentInfo> newCommentInfos = new ArrayList<>(commentInfos);
        newCommentInfos.removeIf(commentInfo -> commentInfo.getId().equals(commentId));

        when(requestDataHolderMock.getRequestParameter(COMMENT_ID_PARAM)).thenReturn(commentIdParam);
        when(requestDataHolderMock.getSessionAttribute(COMMENTS_ATTR)).thenReturn(commentInfos);
        when(requestDataHolderMock.getSessionAttribute(DISH_ID_ATTR)).thenReturn(dishId);
        when(commentServiceMock.getPageCount(dishId)).thenReturn(pageCount);
        when(requestDataHolderMock.getSessionAttribute(CURRENT_PAGE_PAGINATION_ATTR)).thenReturn(currentPageAttr);
        when(requestDataHolderMock.getSessionAttribute(SORT_TYPE_ATTR)).thenReturn(type);
        when(commentServiceMock.getAllByDishIdPagination(dishId, currentPageAttr, type)).thenReturn(newCommentInfos);

        CommandResult expectedResult = new CommandResult(COMMENTS_PAGE, true);
        //when
        CommandResult result = deleteCommentCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(COMMENT_ID_PARAM);
        verify(commentServiceMock).removeComment(commentId);
        verify(requestDataHolderMock).getSessionAttribute(COMMENTS_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(DISH_ID_ATTR);
        verify(commentServiceMock).getPageCount(dishId);
        verify(requestDataHolderMock).putSessionAttribute(PAGE_COUNT_ATTR, pageCount);
        verify(requestDataHolderMock).getSessionAttribute(CURRENT_PAGE_PAGINATION_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(SORT_TYPE_ATTR);
        verify(commentServiceMock).getAllByDishIdPagination(dishId, currentPageAttr, type);
        verify(requestDataHolderMock).putSessionAttribute(COMMENTS_ATTR, newCommentInfos);

        assertEquals(expectedResult, result);
    }


}