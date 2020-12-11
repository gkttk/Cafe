package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentEstimate;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserCommentRatingFieldExtractor implements FieldExtractor<UserCommentRating> {

    private final static String USER_ID_KEY = "user_id";
    private final static String COMMENT_ID_KEY = "comment_id";
    private final static String ESTIMATE_KEY = "estimate";

    @Override
    public Map<String, Object> extractFields(UserCommentRating userCommentRating) {

        Map<String, Object> result = new LinkedHashMap<>();

        Long userId = userCommentRating.getUserId();
        result.put(USER_ID_KEY, userId);

        Long commentId = userCommentRating.getCommentId();
        result.put(COMMENT_ID_KEY, commentId);

        CommentEstimate estimate = userCommentRating.getEstimate();
        String estimateName = estimate.toString();
        result.put(ESTIMATE_KEY, estimateName);

        return result;

    }
}
