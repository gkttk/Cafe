package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserRole;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserInfoFieldExtractor implements FieldExtractor<UserInfo> {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String POINTS_KEY = "points";
    private final static String ROLE_KEY = "role";
    private final static String BLOCKED_KEY = "blocked";


    @Override
    public Map<String, Object> extractFields(UserInfo userInfo) {
        Map<String, Object> result = new LinkedHashMap<>();

        Long id = userInfo.getId();
        result.put(ID_KEY, id);

        String login = userInfo.getLogin();
        result.put(LOGIN_KEY, login);

        int points = userInfo.getPoints();
        result.put(POINTS_KEY, points);

        UserRole role = userInfo.getRole();
        String roleName = role.name();
        result.put(ROLE_KEY, roleName);

        boolean isBlocked = userInfo.isBlocked();
        result.put(BLOCKED_KEY, isBlocked);

        return result;

    }
}
