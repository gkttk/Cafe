package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.dto.CommentInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

public class CommentInfoRowMapperTest {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String DATE_KEY = "creation_date";
    private final static String LOGIN_KEY = "login";
    private final static String IMG_BASE64_KEY = "img_base64";

    private final RowMapper<CommentInfo> rowMapper = new CommentInfoRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long id = 1L;
        String text = "testText";
        int rating = 2;

        LocalDateTime creationDate = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(creationDate);

        String userLogin = "loginTest";
        String base64Img = "imgBase64Test";

        CommentInfo expectedEntity = new CommentInfo(id, text, rating, creationDate, userLogin, base64Img);

        when(resultSetMock.getLong(ID_KEY)).thenReturn(id);
        when(resultSetMock.getString(TEXT_KEY)).thenReturn(text);
        when(resultSetMock.getInt(RATING_KEY)).thenReturn(rating);
        when(resultSetMock.getTimestamp(DATE_KEY)).thenReturn(timestamp);
        when(resultSetMock.getString(LOGIN_KEY)).thenReturn(userLogin);
        when(resultSetMock.getString(IMG_BASE64_KEY)).thenReturn(base64Img);

        //when
        CommentInfo result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}