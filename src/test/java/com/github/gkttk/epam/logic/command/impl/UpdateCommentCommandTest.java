package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdateCommentCommandTest {

    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String COMMENT_TEXT_PARAM = "commentText";
    private final static String DISH_COMMENTS_ATTR = "dishComments";
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";

    private CommentService commentServiceMock;

    private Command updateCommentCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.commentServiceMock = Mockito.mock(CommentService.class);
        this.updateCommentCommand = new UpdateCommentCommand(commentServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCommentsPage() throws ServiceException {
        //given
        String commentIdParam = "3";
        long commentId = Long.parseLong(commentIdParam);
        String commentText = "Hello, world!";

        List<CommentInfo> comments = new ArrayList<>();
        comments.add(new CommentInfo(1L, "text1", 10, LocalDateTime.MIN, "login",
                "img1Base64"));
        comments.add(new CommentInfo(2L, "text2", 15, LocalDateTime.MIN, "login2",
                "img2Base64"));
        comments.add(new CommentInfo(commentId, "text3", 20, LocalDateTime.MIN, "login3",
                "img3Base64"));

        CommentInfo updatedComment = new CommentInfo(commentId, commentText, 20, LocalDateTime.MIN,
                "login3", "img3Base64");

        List<CommentInfo> newComments = new ArrayList<>(comments);
        newComments.removeIf(commentInfo -> commentInfo.getId().equals(commentId));
        newComments.add(updatedComment);

        when(requestDataHolderMock.getRequestParameter(COMMENT_ID_PARAM)).thenReturn(commentIdParam);
        when(requestDataHolderMock.getRequestParameter(COMMENT_TEXT_PARAM)).thenReturn(commentText);
        when(requestDataHolderMock.getSessionAttribute(DISH_COMMENTS_ATTR)).thenReturn(comments);
        when(commentServiceMock.getById(commentId)).thenReturn(Optional.of(updatedComment));

        CommandResult expectedResult = new CommandResult(COMMENTS_PAGE, true);
        //when
        CommandResult result = updateCommentCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(COMMENT_ID_PARAM);
        verify(requestDataHolderMock).getRequestParameter(COMMENT_TEXT_PARAM);
        verify(commentServiceMock).updateComment(commentId, commentText);
        verify(requestDataHolderMock).getSessionAttribute(DISH_COMMENTS_ATTR);
        verify(commentServiceMock).getById(commentId);
        verify(requestDataHolderMock).putSessionAttribute(DISH_COMMENTS_ATTR, newComments);

        assertEquals(expectedResult, result);
    }


}