package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String text = resultSet.getString("text");

        return new Comment(id, text);
    }
}
