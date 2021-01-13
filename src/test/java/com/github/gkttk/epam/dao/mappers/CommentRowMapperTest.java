package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

public class CommentRowMapperTest {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String CREATION_DATE_KEY = "creation_date";
    private final static String USER_ID_KEY = "user_id";
    private final static String DISH_ID_KEY = "dish_id";


    private final RowMapper<Comment> rowMapper = new CommentRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long id = 1L;
        String text = "testText";
        int rating = 0;

        LocalDateTime creationDate = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(creationDate);

        long userId = 2L;
        long dishId = 3L;

        Comment expectedEntity = new Comment(id, text, rating, creationDate, userId, dishId);

        when(resultSetMock.getLong(ID_KEY)).thenReturn(id);
        when(resultSetMock.getString(TEXT_KEY)).thenReturn(text);
        when(resultSetMock.getInt(RATING_KEY)).thenReturn(rating);
        when(resultSetMock.getTimestamp(CREATION_DATE_KEY)).thenReturn(timestamp);
        when(resultSetMock.getLong(USER_ID_KEY)).thenReturn(userId);
        when(resultSetMock.getLong(DISH_ID_KEY)).thenReturn(dishId);

        //when
        Comment result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}