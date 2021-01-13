package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.dto.CommentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommentInfoRowMapper implements RowMapper<CommentInfo> {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String DATE_KEY = "creation_date";
    private final static String LOGIN_KEY = "login";
    private final static String IMG_BASE64_KEY = "img_base64";

    @Override
    public CommentInfo map(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong(ID_KEY);
        String text = resultSet.getString(TEXT_KEY);
        int rating = resultSet.getInt(RATING_KEY);

        Timestamp timestamp = resultSet.getTimestamp(DATE_KEY);
        LocalDateTime dateTime = timestamp.toLocalDateTime();

        String login = resultSet.getString(LOGIN_KEY);
        String imageRef = resultSet.getString(IMG_BASE64_KEY);


        return new CommentInfo(id, text, rating, dateTime, login, imageRef);
    }
}
