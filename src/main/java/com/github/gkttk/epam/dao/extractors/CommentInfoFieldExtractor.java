package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.dto.CommentInfo;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommentInfoFieldExtractor implements FieldExtractor<CommentInfo> {
    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String DATE_KEY = "creation_date";
    private final static String LOGIN_KEY = "login";
    private final static String IMAGE_REF_KEY = "image_ref";

    @Override
    public Map<String, Object> extractFields(CommentInfo commentInfo) {

        Map<String, Object> result = new LinkedHashMap<>();

        Long id = commentInfo.getId();
        result.put(ID_KEY, id);

        String text = commentInfo.getText();
        result.put(TEXT_KEY, text);

        int rating = commentInfo.getRating();
        result.put(RATING_KEY, rating);

        LocalDateTime date = commentInfo.getCreationDate();
        result.put(DATE_KEY, date);

        String login = commentInfo.getUserLogin();
        result.put(LOGIN_KEY, login);

        String imageRef = commentInfo.getUserImageRef();
        result.put(IMAGE_REF_KEY, imageRef);

        return result;

    }
}
