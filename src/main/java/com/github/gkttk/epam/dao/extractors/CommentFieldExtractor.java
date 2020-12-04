package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Comment;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommentFieldExtractor implements FieldExtractor<Comment> {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";

    @Override
    public Map<String, Object> extractFields(Comment comment) {

        Map<String, Object> result = new LinkedHashMap<>();

        Long id = comment.getId();
        result.put(ID_KEY, id);

        String text = comment.getText();
        result.put(TEXT_KEY, text);

        return result;

    }
}
