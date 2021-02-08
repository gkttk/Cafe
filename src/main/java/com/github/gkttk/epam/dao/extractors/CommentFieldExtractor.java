package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Comment;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommentFieldExtractor implements FieldExtractor<Comment> {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String USER_ID_KEY = "user_id";
    private final static String DISH_ID_KEY = "dish_id";

    @Override
    public Map<String, Object> extractFields(Comment comment) {

        Map<String, Object> result = new LinkedHashMap<>();

        Long id = comment.getId();
        result.put(ID_KEY, id);

        String text = comment.getText();
        result.put(TEXT_KEY, text);

        int rating = comment.getRating();
        result.put(RATING_KEY, rating);

        Long userId = comment.getUserId();
        result.put(USER_ID_KEY, userId);

        Long dishId = comment.getDishId();
        result.put(DISH_ID_KEY, dishId);

        return result;

    }
}
