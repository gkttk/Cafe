package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.dto.CommentInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommentInfoFieldExtractorTest {

    private final static String ID_KEY = "id";
    private final static String TEXT_KEY = "text";
    private final static String RATING_KEY = "rating";
    private final static String DATE_KEY = "creation_date";
    private final static String LOGIN_KEY = "login";
    private final static String IMAGE_REF_KEY = "image_ref";

    private final FieldExtractor<CommentInfo> fieldExtractor = new CommentInfoFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long id = 1L;
        String text = "testText";
        int rating = 2;
        LocalDateTime creationDate = LocalDateTime.MIN;
        String userLogin = "loginTest";
        String base64Img = "imgBase64Test";

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(ID_KEY, id);
        expectedMap.put(TEXT_KEY, text);
        expectedMap.put(RATING_KEY, rating);
        expectedMap.put(DATE_KEY, creationDate);
        expectedMap.put(LOGIN_KEY, userLogin);
        expectedMap.put(IMAGE_REF_KEY, base64Img);

        CommentInfo commentInfo = new CommentInfo(id, text, rating, creationDate, userLogin, base64Img);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(commentInfo);
        //then
        Assertions.assertEquals(expectedMap, result);

    }
}