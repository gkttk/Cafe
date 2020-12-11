package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentEstimate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCommentRatingRowMapper implements RowMapper<UserCommentRating> {
    @Override
    public UserCommentRating map(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        long commentId = resultSet.getLong("comment_id");

        String estimateStr = resultSet.getString("estimate");
        CommentEstimate estimate = CommentEstimate.valueOf(estimateStr);

        return new UserCommentRating(userId, commentId, estimate);

    }
}
