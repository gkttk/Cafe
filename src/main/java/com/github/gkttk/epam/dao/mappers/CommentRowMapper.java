package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String text = resultSet.getString("text");
        int rating = resultSet.getInt("rating");
        Timestamp timestamp = resultSet.getTimestamp("creation_date");
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        int userId = resultSet.getInt("user_id");
        int dishId = resultSet.getInt("dish_id");

        return new Comment(id, text, rating, dateTime, userId, dishId);
    }
}
