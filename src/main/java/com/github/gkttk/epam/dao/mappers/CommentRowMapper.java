package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommentRowMapper implements RowMapper<Comment> {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String CREATION_DATE_KEY = "creation_date";
    private final static String USER_ID_KEY = "user_id";
    private final static String DISH_ID_KEY = "dish_id";


    @Override
    public Comment map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_KEY);

        String text = resultSet.getString(TEXT_KEY);

        int rating = resultSet.getInt(RATING_KEY);

        Timestamp timestamp = resultSet.getTimestamp(CREATION_DATE_KEY);
        LocalDateTime dateTime = timestamp.toLocalDateTime();

        Long userId = resultSet.getLong(USER_ID_KEY);

        Long dishId = resultSet.getLong(DISH_ID_KEY);

        return new Comment(id, text, rating, dateTime, userId, dishId);
    }
}
