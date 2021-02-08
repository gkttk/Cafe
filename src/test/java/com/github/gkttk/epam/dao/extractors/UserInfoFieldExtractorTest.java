package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserInfoFieldExtractorTest {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String POINTS_KEY = "points";
    private final static String ROLE_KEY = "role";
    private final static String BLOCKED_KEY = "blocked";

    private final FieldExtractor<UserInfo> fieldExtractor = new UserInfoFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long id = 1L;
        String login = "testLogin";
        UserRole role = UserRole.USER;
        int points = 50;
        boolean isBlocked = false;

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(ID_KEY, id);
        expectedMap.put(LOGIN_KEY, login);
        expectedMap.put(ROLE_KEY, role.name());
        expectedMap.put(POINTS_KEY, points);
        expectedMap.put(BLOCKED_KEY, isBlocked);

        UserInfo userInfo = new UserInfo(id, login, role, points, isBlocked);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(userInfo);
        //then
        Assertions.assertEquals(expectedMap, result);

    }
}