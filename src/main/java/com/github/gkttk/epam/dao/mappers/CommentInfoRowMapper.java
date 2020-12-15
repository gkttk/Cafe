package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.dto.CommentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommentInfoRowMapper implements RowMapper<CommentInfo> {

    @Override
    public CommentInfo map(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        String text = resultSet.getString("text");
        int rating = resultSet.getInt("rating");
        Timestamp timestamp = resultSet.getTimestamp("creation_date");
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        String login = resultSet.getString("login");
        String imageRef = resultSet.getString("image_ref");


        return new CommentInfo(id, text, rating, dateTime, login, imageRef);
    }
}
