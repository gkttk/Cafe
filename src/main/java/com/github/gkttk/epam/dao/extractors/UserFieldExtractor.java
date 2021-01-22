package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.logic.interpreter.Base64Encoder;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserFieldExtractor implements FieldExtractor<User> {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String ROLE_KEY = "role";
    private final static String POINTS_KEY = "points";
    private final static String MONEY_KEY = "money";
    private final static String BLOCKED_KEY = "blocked";
    private final static String AVATAR_KEY = "avatar";

    @Override
    public Map<String, Object> extractFields(User user) {
        Map<String, Object> result = new LinkedHashMap<>();

        Long id = user.getId();
        result.put(ID_KEY, id);

        String login = user.getLogin();
        result.put(LOGIN_KEY, login);

        UserRole role = user.getRole();
        String roleName = role.name();
        result.put(ROLE_KEY, roleName);

        int points = user.getPoints();
        result.put(POINTS_KEY, points);

        BigDecimal money = user.getMoney();
        result.put(MONEY_KEY, money);

        boolean isBlocked = user.isBlocked();
        result.put(BLOCKED_KEY, isBlocked);



        String imageRef = user.getImgBase64();
        byte[] avatar = Base64.getDecoder().decode(imageRef);
        result.put(AVATAR_KEY, avatar);

        return result;

    }
}
