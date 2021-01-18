package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentSortType;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DishCommentsCommandTest {

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String DISH_ID_PARAM = "dishId";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String PAGE_NUMBER = "pageNumber";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String PAGE_COUNT_ATTR = "pageCount";
    private final static String ESTIMATES_ATTR = "estimates";
    private final static String SORT_TYPE_ATTR = "sortType";
    private final static String CURRENT_PAGE_PAGINATION_ATTR = "currentPagePagination";

    private final static User TEST_USER = new User(1L, "testLogin", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private CommentService commentServiceMock;
    private UserCommentRatingService userCommentRatingServiceMock;

    private RequestDataHolder requestDataHolderMock;

    private Command dishCommentsCommand;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.commentServiceMock = Mockito.mock(CommentService.class);
        this.userCommentRatingServiceMock = Mockito.mock(UserCommentRatingService.class);
        this.dishCommentsCommand = new DishCommentsCommand(commentServiceMock, userCommentRatingServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCommentsPage() throws ServiceException {
        //given
        long userId = TEST_USER.getId();
        String dishIdParam = "2";
        long dishId = Long.parseLong(dishIdParam);
        List<UserCommentRating> userCommentRatings = new ArrayList<>();
        userCommentRatings.add(new UserCommentRating(userId, dishId, true));
        userCommentRatings.add(new UserCommentRating(2L, 3L, false));
        userCommentRatings.add(new UserCommentRating(4L, 6L, true));

        Map<Long, Boolean> userEstimates = new HashMap<>();
        for (UserCommentRating userCommentRating : userCommentRatings) {
            long commentId = userCommentRating.getCommentId();
            boolean isLiked = userCommentRating.isLiked();
            userEstimates.put(commentId, isLiked);
        }
        String pageNumberParam = "2";
        int pageNumber = Integer.parseInt(pageNumberParam);
        int pageCount = 5;
        CommentSortType sortType = CommentSortType.DATE;

        List<CommentInfo> comments = Arrays.asList(null, null, null);

        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(requestDataHolderMock.getRequestParameter(DISH_ID_PARAM)).thenReturn(dishIdParam);
        when(requestDataHolderMock.isSessionContainKey(ESTIMATES_ATTR)).thenReturn(false);
        when(userCommentRatingServiceMock.getAllByUserIdAndDishId(userId, dishId)).thenReturn(userCommentRatings);
        when(requestDataHolderMock.isRequestParamContainsKey(PAGE_NUMBER)).thenReturn(true);
        when(requestDataHolderMock.getRequestParameter(PAGE_NUMBER)).thenReturn(pageNumberParam);
        when(commentServiceMock.getPageCount(dishId)).thenReturn(pageCount);
        when(requestDataHolderMock.isSessionContainKey(SORT_TYPE_ATTR)).thenReturn(true);
        when(requestDataHolderMock.getSessionAttribute(SORT_TYPE_ATTR)).thenReturn(sortType);
        when(commentServiceMock.getAllByDishIdPagination(dishId, pageNumber, sortType)).thenReturn(comments);

        CommandResult expectedResult = new CommandResult(COMMENTS_PAGE, true);
        //when
        CommandResult result = dishCommentsCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(requestDataHolderMock).getRequestParameter(DISH_ID_PARAM);
        verify(requestDataHolderMock).putSessionAttribute(DISH_ID_ATTR, dishId);
        verify(requestDataHolderMock).isSessionContainKey(ESTIMATES_ATTR);
        verify(userCommentRatingServiceMock).getAllByUserIdAndDishId(userId, dishId);
        verify(requestDataHolderMock).putSessionAttribute(ESTIMATES_ATTR, userEstimates);
        verify(requestDataHolderMock).isRequestParamContainsKey(PAGE_NUMBER);
        verify(requestDataHolderMock).getRequestParameter(PAGE_NUMBER);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PAGINATION_ATTR, pageNumber);
        verify(commentServiceMock).getPageCount(dishId);
        verify(requestDataHolderMock).putSessionAttribute(PAGE_COUNT_ATTR, pageCount);
        verify(requestDataHolderMock).isSessionContainKey(SORT_TYPE_ATTR);
        verify(requestDataHolderMock).getSessionAttribute(SORT_TYPE_ATTR);
        verify(commentServiceMock).getAllByDishIdPagination(dishId, pageNumber, sortType);
        verify(requestDataHolderMock).putSessionAttribute(COMMENTS_ATTR, comments);
        verify(requestDataHolderMock).putSessionAttribute(CURRENT_PAGE_PARAM, COMMENTS_PAGE);
        
        assertEquals(expectedResult, result);
    }


}