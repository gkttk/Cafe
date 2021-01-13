package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCommentRatingRowMapper implements RowMapper<UserCommentRating> {

    private final static String USER_ID_KEY = "user_id";
    private final static String COMMENT_ID_KEY = "comment_id";
    private final static String IS_LIKED_KEY = "liked";

    @Override
    public UserCommentRating map(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong(USER_ID_KEY);
        long commentId = resultSet.getLong(COMMENT_ID_KEY);

        boolean isLiked = resultSet.getBoolean(IS_LIKED_KEY);

        return new UserCommentRating(userId, commentId, isLiked);

    }
}
