package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserFieldExtractorTest {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String ROLE_KEY = "role";
    private final static String POINTS_KEY = "points";
    private final static String MONEY_KEY = "money";
    private final static String BLOCKED_KEY = "blocked";
    private final static String AVATAR_KEY = "avatar";

    private final FieldExtractor<User> fieldExtractor = new UserFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long userId = 1L;
        String login = "testLogin";
        UserRole role = UserRole.USER;
        int points = 50;
        BigDecimal money = BigDecimal.TEN;
        boolean isBlocked = false;
        String imgBase64 = "dGVzdEltZ0Jhc2U2NA==";
        byte[] avatar = Base64.getDecoder().decode(imgBase64);

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(ID_KEY, userId);
        expectedMap.put(LOGIN_KEY, login);
        expectedMap.put(ROLE_KEY, role.name());
        expectedMap.put(POINTS_KEY, points);
        expectedMap.put(MONEY_KEY, money);
        expectedMap.put(BLOCKED_KEY, isBlocked);
        expectedMap.put(AVATAR_KEY, avatar);

        User user = new User(userId, login, role, points, money, isBlocked, imgBase64);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(user);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.get(ID_KEY), userId),
                () -> Assertions.assertEquals(result.get(LOGIN_KEY), login),
                () -> Assertions.assertEquals(result.get(ROLE_KEY), role.name()),
                () -> Assertions.assertEquals(result.get(POINTS_KEY), points),
                () -> Assertions.assertEquals(result.get(MONEY_KEY), money),
                () -> Assertions.assertEquals(result.get(BLOCKED_KEY), isBlocked),
                () -> Assertions.assertArrayEquals((byte[]) result.get(AVATAR_KEY), avatar)
        );

    }
}