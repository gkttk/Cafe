package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.dto.impl.CommentInfoDaoImpl;
import com.github.gkttk.epam.dao.entity.impl.CommentDaoImpl;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.Comment;
import com.github.gkttk.epam.model.enums.CommentSortTypes;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {


    private static CommentServiceImpl commentService;
    private static CommentDaoImpl commentDaoMock;
    private static CommentInfoDaoImpl commentInfoDaoMock;
    private static Optional<CommentInfo> expectedCommentInfoOpt;

    @BeforeEach
    void init() {
        expectedCommentInfoOpt = Optional.of(new CommentInfo(-1L,
                "testText",
                -100, LocalDateTime.MIN,
                "testLogin",
                "testAvatarBase64"));

        DaoHelperFactory daoHelperFactoryMock = Mockito.mock(DaoHelperFactory.class);
        DaoHelperImpl daoHelperMock = Mockito.mock(DaoHelperImpl.class);
        commentService = new CommentServiceImpl(daoHelperFactoryMock);
        commentDaoMock = Mockito.mock(CommentDaoImpl.class);
        commentInfoDaoMock = Mockito.mock(CommentInfoDaoImpl.class);
        when(daoHelperFactoryMock.createDaoHelper()).thenReturn(daoHelperMock);
        when(daoHelperMock.createCommentDao()).thenReturn(commentDaoMock);
        when(daoHelperMock.createCommentInfoDao()).thenReturn(commentInfoDaoMock);
    }

    @Test
    public void testGetByIdShouldReturnOptionalWhenCommentInfoWhenCommentWithGivenIdExistsInDb() throws DaoException, ServiceException {
        //given
        long correctId = 100L;
        when(commentInfoDaoMock.findByCommentId(correctId)).thenReturn(expectedCommentInfoOpt);
        //when
        Optional<CommentInfo> result = commentService.getById(correctId);
        //then
        Assertions.assertEquals(result, expectedCommentInfoOpt);
    }

    @Test
    public void testGetByIdShouldReturnEmptyOptionalWhenCommentWithGivenIdDoesNotExistInDb() throws DaoException, ServiceException {
        //given
        long incorrectId = -10L;
        when(commentInfoDaoMock.findByCommentId(incorrectId)).thenReturn(Optional.empty());
        //when
        Optional<CommentInfo> result = commentService.getById(incorrectId);
        //then
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetByIdShouldThrowExceptionWhenCantGetDataFromDb() throws DaoException {
        //given
        long id = 5L;
        when(commentInfoDaoMock.findByCommentId(id)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> commentService.getById(id));
    }

    @Test
    public void testAddCommentShouldReturnCommentId() throws DaoException, ServiceException {
        //given
        String commentText = "Hello, world!";
        long userId = 1;
        long dishId = 2;
        Comment expectedComment = new Comment(null, commentText, userId, dishId);
        long expectedId = 100L;
        when(commentDaoMock.save(expectedComment)).thenReturn(expectedId);
        //when
        Long resultId = commentService.addComment(commentText, userId, dishId);
        //then
        Assertions.assertEquals(expectedId, resultId);
    }

    @Test
    public void testAddCommentShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException, ServiceException {
        //given
        String commentText = "Hello, world!";
        long userId = 1;
        long dishId = 2;
        Comment expectedComment = new Comment(null, commentText, userId, dishId);
        when(commentDaoMock.save(expectedComment)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class,
                () -> commentService.addComment(commentText, userId, dishId));
    }

    @ParameterizedTest
    @MethodSource("parameterTestGetAllByDishIdPaginationShouldReturnCorrectSizeListProvider")
    public void testGetAllByDishIdPaginationShouldReturnCorrectSizeList(long dishId, int currentPage, CommentSortTypes type, int expectedSize) throws DaoException, ServiceException {
        //given
        when(commentInfoDaoMock.findAllByDishIdOrderDatePagination(Mockito.eq(dishId), anyInt(), anyInt()))
                .thenReturn(Arrays.asList(null, null, null, null, null));
        when(commentInfoDaoMock.findAllByDishIdOrderRatingPagination(Mockito.eq(dishId), anyInt(), anyInt()))
                .thenReturn(Arrays.asList(null, null, null));
        //when
        List<CommentInfo> result = commentService.getAllByDishIdPagination(dishId, currentPage, type);
        //then
        Assertions.assertEquals(expectedSize, result.size());
    }

    @DataProvider
    public static Object[][] parameterTestGetAllByDishIdPaginationShouldReturnCorrectSizeListProvider() {
        return new Object[][]{
                {5L, 2, CommentSortTypes.DATE, 5},
                {10L, 1, CommentSortTypes.RATING, 3}

        };
    }

    @ParameterizedTest
    @MethodSource("parameterTestGetAllByDishIdPaginationShouldThrowExceptionWhenCantGetAccessToDbProvider")
    public void testGetAllByDishIdPaginationShouldThrowExceptionWhenCantGetAccessToDb(long dishId, int currentPage, CommentSortTypes type) throws DaoException {
        //given
        when(commentInfoDaoMock.findAllByDishIdOrderRatingPagination(Mockito.eq(dishId), anyInt(), anyInt())).thenThrow(new DaoException());
        when(commentInfoDaoMock.findAllByDishIdOrderDatePagination(Mockito.eq(dishId), anyInt(), anyInt())).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> commentService.getAllByDishIdPagination(dishId, currentPage, type));

    }

    @DataProvider
    public static Object[][] parameterTestGetAllByDishIdPaginationShouldThrowExceptionWhenCantGetAccessToDbProvider() {
        return new Object[][]{
                {5L, 2, CommentSortTypes.DATE},
                {10L, 1, CommentSortTypes.RATING}
        };
    }


    @Test
    public void testGetPageCountShouldReturnPageCountByDishId() throws DaoException, ServiceException {
        //given
        long dishId = 1L;
        int commentCount = 10;
        int expectedPageCount = 2;
        when(commentDaoMock.rowCountForDishId(dishId)).thenReturn(commentCount);
        //when
        int result = commentService.getPageCount(dishId);
        //then
        Assertions.assertEquals(expectedPageCount, result);
    }

    @Test
    public void testGetPageCountShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException, ServiceException {
        //given
        long dishId = 1L;
        when(commentDaoMock.rowCountForDishId(dishId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> commentService.getPageCount(dishId));
    }

    @Test
    public void testUpdateCommentShouldInvokeMethods() throws DaoException, ServiceException {
        //given
        long commentId = 1L;
        long userId = 2L;
        long dishId = 3L;
        String newCommentText = "New comment text!";
        Comment oldComment = new Comment(commentId, "", userId, dishId);
        Comment newComment = new Comment(commentId, newCommentText, userId, dishId);
        when(commentDaoMock.findById(commentId)).thenReturn(Optional.of(oldComment));
        //when
        commentService.updateComment(commentId, newCommentText);
        //then
        verify(commentDaoMock).save(newComment);
    }

    @Test
    public void testUpdateCommentShouldThrowExceptionWhenInvokeFindByIdAndCantGetAccessToDb() throws DaoException, ServiceException {
        //given
        long commentId = 1L;
        String newCommentText = "New comment text!";
        when(commentDaoMock.findById(commentId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> commentService.updateComment(commentId, newCommentText));
    }

    @Test
    public void testUpdateCommentShouldThrowExceptionWhenInvokeSaveAndCantGetAccessToDb() throws DaoException, ServiceException {
        //given
        long commentId = 1L;
        long userId = 2L;
        long dishId = 3L;
        String newCommentText = "New comment text!";
        Comment oldComment = new Comment(commentId, "", userId, dishId);
        when(commentDaoMock.findById(commentId)).thenReturn(Optional.of(oldComment));
        when(commentDaoMock.save(any())).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> commentService.updateComment(commentId, newCommentText));
    }


    @Test
    public void testRemoveCommentShouldInvokeMethods() throws DaoException, ServiceException {
        //given
        long commentId = 1L;
        //when
        commentService.removeComment(commentId);
        //then
        verify(commentDaoMock, times(1)).removeById(commentId);
    }

    @Test
    public void testRemoveCommentShouldThrowExceptionWhenInvokeRemoveByIdAndCantGetAccessToDb() throws DaoException, ServiceException {
        //given
        long commentId = 1L;
        doThrow(new DaoException()).when(commentDaoMock).removeById(anyLong());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> commentService.removeComment(commentId));
    }

}
