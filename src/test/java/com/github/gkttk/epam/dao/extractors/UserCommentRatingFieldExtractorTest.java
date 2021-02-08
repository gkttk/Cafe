package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.UserCommentRating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserCommentRatingFieldExtractorTest {

    private final static String USER_ID_KEY = "user_id";
    private final static String COMMENT_ID_KEY = "comment_id";
    private final static String IS_LIKED_KEY = "liked";

    private final FieldExtractor<UserCommentRating> fieldExtractor = new UserCommentRatingFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long userId = 1L;
        long commentId = 2L;
        boolean isLiked = true;

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(USER_ID_KEY, userId);
        expectedMap.put(COMMENT_ID_KEY, commentId);
        expectedMap.put(IS_LIKED_KEY, isLiked);

        UserCommentRating userCommentRating = new UserCommentRating(userId, commentId, isLiked);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(userCommentRating);
        //then
        Assertions.assertEquals(expectedMap, result);

    }
}