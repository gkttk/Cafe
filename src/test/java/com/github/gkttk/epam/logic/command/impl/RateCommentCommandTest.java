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
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RateCommentCommandTest {

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String ESTIMATE_PARAM = "estimate";
    private final static String DISH_COMMENTS_ATTR = "dishComments";
    private final static String ESTIMATES_ATTR = "estimates";
    private final static String DISH_ID_ATTR = "dishId";

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private CommentService commentServiceMock;
    private UserCommentRatingService userCommentRatingServiceMock;

    private Command rateCommentCommand;
    private RequestDataHolder requestDataHolderMock;

    @BeforeEach
    void init() {
        this.requestDataHolderMock = Mockito.mock(RequestDataHolder.class);
        this.commentServiceMock = Mockito.mock(CommentService.class);
        this.userCommentRatingServiceMock = Mockito.mock(UserCommentRatingService.class);
        this.rateCommentCommand = new RateCommentCommand(commentServiceMock, userCommentRatingServiceMock);
    }

    @Test
    void testExecuteShouldReturnCommandResultWithRedirectToCommentsPage() throws ServiceException {
        //given
        long userId = TEST_USER.getId();

        String commentIdParam = "2";
        long commentId = Long.parseLong(commentIdParam);

        String isLikeParam = "true";
        boolean isLike = Boolean.parseBoolean(isLikeParam);

        CommentInfo comment1 = new CommentInfo(1L, "text1", 10, LocalDateTime.MIN, "login1",
                "img1Base64");
        CommentInfo comment2 = new CommentInfo(2L, "text2", 20, LocalDateTime.MIN, "login2",
                "img2Base64");
        CommentInfo comment3 = new CommentInfo(3L, "text3", 30, LocalDateTime.MIN, "login3",
                "img3Base64");

        List<CommentInfo> dishComments = new ArrayList<>();
        dishComments.add(comment1);
        dishComments.add(comment2);
        dishComments.add(comment3);

        long dishId = 2L;

        List<UserCommentRating> userCommentRatings = new ArrayList<>();
        userCommentRatings.add(new UserCommentRating(userId, commentId, true));
        userCommentRatings.add(new UserCommentRating(userId, 3L, false));
        userCommentRatings.add(new UserCommentRating(userId, 5L, true));

        Map<Long, Boolean> userCommentRatingsMap = userCommentRatings.stream()
                .collect(Collectors.toMap(UserCommentRating::getCommentId, UserCommentRating::isLiked));


        when(requestDataHolderMock.getSessionAttribute(AUTH_USER_ATTR)).thenReturn(TEST_USER);
        when(requestDataHolderMock.getRequestParameter(COMMENT_ID_PARAM)).thenReturn(commentIdParam);
        when(requestDataHolderMock.getRequestParameter(ESTIMATE_PARAM)).thenReturn(isLikeParam);
        when(requestDataHolderMock.getSessionAttribute(DISH_COMMENTS_ATTR)).thenReturn(dishComments);
        when(commentServiceMock.getById(1L)).thenReturn(Optional.of(comment1));
        when(commentServiceMock.getById(2L)).thenReturn(Optional.of(comment2));
        when(commentServiceMock.getById(3L)).thenReturn(Optional.of(comment3));
        when(requestDataHolderMock.getSessionAttribute(DISH_ID_ATTR)).thenReturn(dishId);
        when(userCommentRatingServiceMock.getAllByUserIdAndDishId(userId, dishId)).thenReturn(userCommentRatings);


        CommandResult expectedResult = new CommandResult(COMMENTS_PAGE, true);
        //when
        CommandResult result = rateCommentCommand.execute(requestDataHolderMock);
        //then
        verify(requestDataHolderMock).getSessionAttribute(AUTH_USER_ATTR);
        verify(requestDataHolderMock).getRequestParameter(COMMENT_ID_PARAM);
        verify(requestDataHolderMock).getRequestParameter(ESTIMATE_PARAM);
        verify(userCommentRatingServiceMock).evaluateComment(userId, commentId, isLike);
        verify(requestDataHolderMock).getSessionAttribute(DISH_COMMENTS_ATTR);
        verify(commentServiceMock).getById(1L);
        verify(commentServiceMock).getById(2L);
        verify(commentServiceMock).getById(3L);
        verify(requestDataHolderMock).putSessionAttribute(DISH_COMMENTS_ATTR, dishComments);
        verify(requestDataHolderMock).getSessionAttribute(DISH_ID_ATTR);
        verify(userCommentRatingServiceMock).getAllByUserIdAndDishId(userId, dishId);
        verify(requestDataHolderMock).putSessionAttribute(ESTIMATES_ATTR, userCommentRatingsMap);

        assertEquals(expectedResult, result);
    }


}