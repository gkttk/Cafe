package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommentFieldExtractorTest {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String USER_ID_KEY = "user_id";
    private final static String DISH_ID_KEY = "dish_id";

    private final FieldExtractor<Comment> fieldExtractor = new CommentFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long id = 1L;
        String text = "testText";
        int rating = 0;
        long userId = 2L;
        long dishId = 3L;

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(ID_KEY, id);
        expectedMap.put(TEXT_KEY, text);
        expectedMap.put(RATING_KEY, rating);
        expectedMap.put(USER_ID_KEY, userId);
        expectedMap.put(DISH_ID_KEY, dishId);
        Comment comment = new Comment(id, text, userId, dishId);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(comment);
        //then
        Assertions.assertEquals(expectedMap, result);

    }
}