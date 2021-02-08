package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.UserCommentRating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class UserCommentRatingRowMapperTest {

    private final static String USER_ID_KEY = "user_id";
    private final static String COMMENT_ID_KEY = "comment_id";
    private final static String IS_LIKED_KEY = "liked";

    private final RowMapper<UserCommentRating> rowMapper = new UserCommentRatingRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long userId = 1L;
        long commentId = 2L;
        boolean isLiked = true;

        UserCommentRating expectedEntity = new UserCommentRating(userId, commentId, isLiked);

        when(resultSetMock.getLong(USER_ID_KEY)).thenReturn(userId);
        when(resultSetMock.getLong(COMMENT_ID_KEY)).thenReturn(commentId);
        when(resultSetMock.getBoolean(IS_LIKED_KEY)).thenReturn(isLiked);
        //when
        UserCommentRating result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}