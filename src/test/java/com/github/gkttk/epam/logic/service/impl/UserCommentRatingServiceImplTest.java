package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.impl.CommentDaoImpl;
import com.github.gkttk.epam.dao.entity.impl.UserCommentRatingDaoImpl;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Comment;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserCommentRatingServiceImplTest {

    private static DaoHelperImpl daoHelperMock;
    private static UserCommentRatingServiceImpl userCommentRatingService;
    private static UserCommentRatingDaoImpl userCommentRatingDaoMock;
    private static CommentDaoImpl commentDaoMock;

    @BeforeEach
    void init() {
        DaoHelperFactory daoHelperFactoryMock = Mockito.mock(DaoHelperFactory.class);
        daoHelperMock = Mockito.mock(DaoHelperImpl.class);

        userCommentRatingService = new UserCommentRatingServiceImpl(daoHelperFactoryMock);
        userCommentRatingDaoMock = Mockito.mock(UserCommentRatingDaoImpl.class);
        commentDaoMock = Mockito.mock(CommentDaoImpl.class);

        when(daoHelperFactoryMock.createDaoHelper()).thenReturn(daoHelperMock);
        when(daoHelperMock.createUserCommentRatingDao()).thenReturn(userCommentRatingDaoMock);
        when(daoHelperMock.createCommentDao()).thenReturn(commentDaoMock);
    }


    @Test
    public void testGetAllByUserIdAndDishIdShouldReturnUserCommentRatingList() throws DaoException, ServiceException {
        //given
        int expectedSize = 3;
        long userId = 1L;
        long dishId = 2L;
        when(userCommentRatingDaoMock.findAllByUserIdAndDishId(userId, dishId)).thenReturn(Arrays.asList(null, null, null));
        //when
        List<UserCommentRating> result = userCommentRatingService.getAllByUserIdAndDishId(userId, dishId);
        //then
        verify(userCommentRatingDaoMock).findAllByUserIdAndDishId(userId, dishId);
        Assertions.assertEquals(expectedSize, result.size());
    }


    @Test
    public void testGetAllByUserIdAndDishIdShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        long userId = 1L;
        long dishId = 2L;
        when(userCommentRatingDaoMock.findAllByUserIdAndDishId(userId, dishId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class,
                () -> userCommentRatingService.getAllByUserIdAndDishId(userId, dishId));
        verify(userCommentRatingDaoMock).findAllByUserIdAndDishId(userId, dishId);
    }

    @ParameterizedTest
    @MethodSource("parameterTestEvaluateCommentShouldInvokeMethodsProvider")
    public void testEvaluateCommentShouldInvokeMethods(boolean isLiked, Optional<UserCommentRating> userCommentRatingOpt)
            throws ServiceException, DaoException {
        //given
        long userId = 1L;
        long commentId = 2L;

        Comment comment = new Comment(commentId, "testText", 0, LocalDateTime.MAX, userId, 10L);
        int newCommentRating = isLiked ? (comment.getRating() + 1) : (comment.getRating() - 1);
        Comment changedComment = new Comment(commentId, "testText", newCommentRating, LocalDateTime.MAX, userId, 10L);

        when(userCommentRatingDaoMock.getByUserIdAndCommentId(userId, commentId)).thenReturn(userCommentRatingOpt);
        when(commentDaoMock.findById(commentId)).thenReturn(Optional.of(comment));
        //when
        userCommentRatingService.rateComment(userId, commentId, isLiked);
        //then
        verify(daoHelperMock).startTransaction();
        if (userCommentRatingOpt.isPresent()) {
            verify(userCommentRatingDaoMock).removeByUserIdAndCommentId(userId, commentId);
        } else {
            UserCommentRating userCommentRatingForDb = new UserCommentRating(userId, commentId, isLiked);
            verify(userCommentRatingDaoMock).save(userCommentRatingForDb);
        }
        verify(commentDaoMock).findById(commentId);
        verify(commentDaoMock).save(changedComment);
        verify(daoHelperMock).commit();

    }

    @DataProvider
    public static Object[][] parameterTestEvaluateCommentShouldInvokeMethodsProvider() {
        return new Object[][]{
                {true, Optional.empty()},
                {false, Optional.empty()},
                {false, Optional.of(new UserCommentRating(1L, 2L, true))},
                {true, Optional.of(new UserCommentRating(1L, 2L, false))}
        };
    }

    @Test
    public void testEvaluateCommentShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        long userId = 1L;
        long commentId = 2L;
        boolean isLiked = true;
        doThrow(new DaoException()).when(daoHelperMock).startTransaction();
        //when
        //then
        Assertions.assertThrows(ServiceException.class,
                () -> userCommentRatingService.rateComment(userId, commentId, isLiked));
        verify(daoHelperMock).startTransaction();
        verify(daoHelperMock).rollback();
        verify(daoHelperMock).endTransaction();
        verify(daoHelperMock).close();
    }


}
