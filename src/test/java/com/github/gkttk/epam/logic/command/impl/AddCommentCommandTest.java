package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.logic.service.impl.CommentServiceImpl;
import com.github.gkttk.epam.logic.validator.CommentValidator;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.CommentSortTypes;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddCommentCommandTest {

    private final static String COMMENT_TEXT_PARAM = "commentText";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String PAGE_COUNT_ATTR = "pageCount";
    private final static String CURRENT_PAGE_PAGINATION_ATTR = "currentPagePagination";
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String SORT_TYPE_ATTR = "sortType";
    private final static String ERROR_MESSAGE_KEY = "errorMessage";
    private final static String ERROR_MESSAGE_VALUE = "error.message.wrong.comment";
    private final static int START_PAGE_PAGINATION = 1;

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private CommentService commentServiceMock;
    private Validator commentValidatorMock;
    private Command addCommentCommand;

    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.commentServiceMock = Mockito.mock(CommentServiceImpl.class);
        this.commentValidatorMock = Mockito.mock(CommentValidator.class);
        this.addCommentCommand = new AddCommentCommand(commentServiceMock, commentValidatorMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCommentsPageWhenCommentTextIsValid() throws ServiceException {
        //given
        String commentTextValue = "Comment text value test";
        long userId = TEST_USER.getId();
        long dishId = 2L;
        int pageCount = 5;
        CommentSortTypes sortType = CommentSortTypes.DATE;
        List<CommentInfo> comments = Arrays.asList(null, null, null);

        CommandResult expectedResult = new CommandResult(COMMENTS_PAGE, true);

        when(requestDataHolderMock.getRequestParameter(COMMENT_TEXT_PARAM)).thenReturn(commentTextValue);
        when(commentValidatorMock.validate(commentTextValue)).thenReturn(true);
        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(requestDataHolderMock.getSessionAttribute(DISH_ID_ATTR)).thenReturn(dishId);
        when(commentServiceMock.getPageCount(dishId)).thenReturn(pageCount);
        when(requestDataHolderMock.getSessionAttribute(SORT_TYPE_ATTR)).thenReturn(sortType);
        when(commentServiceMock.getAllByDishIdPagination(dishId, START_PAGE_PAGINATION, sortType))
                .thenReturn(comments);

        //when
        CommandResult result = addCommentCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(COMMENT_TEXT_PARAM);
        verify(commentValidatorMock).validate(commentTextValue);
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(DISH_ID_ATTR);
        verify(commentServiceMock).addComment(commentTextValue, userId, dishId);
        verify(commentServiceMock).getPageCount(dishId);

        verify(requestDataHolderMock).putSessionAttribute(PAGE_COUNT_ATTR, pageCount);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PAGINATION_ATTR, START_PAGE_PAGINATION);
        verify(requestDataHolderMock).getSessionAttribute(SORT_TYPE_ATTR);
        verify(commentServiceMock).getAllByDishIdPagination(dishId, START_PAGE_PAGINATION, sortType);
        verify(requestDataHolderMock).putSessionAttribute(COMMENTS_ATTR, comments);

        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithForwardToCommentsPageWhenCommentTextIsNotValid() throws ServiceException {
        //given
        String incorrectCommentText = Stream.generate(() -> "a").limit(251).collect(Collectors.joining());
        when(requestDataHolderMock.getRequestParameter(COMMENT_TEXT_PARAM)).thenReturn(incorrectCommentText);
        when(commentValidatorMock.validate(incorrectCommentText)).thenReturn(false);
        CommandResult expectedResult = new CommandResult(COMMENTS_PAGE, false);
        //when
        CommandResult result = addCommentCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getRequestParameter(COMMENT_TEXT_PARAM);
        verify(commentValidatorMock).validate(incorrectCommentText);
        verify(requestDataHolderMock).putRequestAttribute(ERROR_MESSAGE_KEY, ERROR_MESSAGE_VALUE);
        assertEquals(expectedResult, result);
    }

}