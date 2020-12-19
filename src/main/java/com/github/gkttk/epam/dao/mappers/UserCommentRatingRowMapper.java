package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCommentRatingRowMapper implements RowMapper<UserCommentRating> {
    @Override
    public UserCommentRating map(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        long commentId = resultSet.getLong("comment_id");

        boolean isLiked = resultSet.getBoolean("liked");


        return new UserCommentRating(userId, commentId, isLiked);

    }
}
