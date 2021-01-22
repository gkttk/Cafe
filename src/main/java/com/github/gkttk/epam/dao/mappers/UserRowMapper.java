package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class UserRowMapper implements RowMapper<User> {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String ROLE_KEY = "role";
    private final static String POINTS_KEY = "points";
    private final static String MONEY_KEY = "money";
    private final static String BLOCKED_KEY = "blocked";
    private final static String AVATAR_KEY = "avatar";

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_KEY);

        String login = resultSet.getString(LOGIN_KEY);

        String roleName = resultSet.getString(ROLE_KEY);
        UserRole role = UserRole.valueOf(roleName);

        int points = resultSet.getInt(POINTS_KEY);

        BigDecimal money = resultSet.getBigDecimal(MONEY_KEY);

        boolean isBlocked = resultSet.getBoolean(BLOCKED_KEY);

        Blob blob = resultSet.getBlob(AVATAR_KEY);
        byte[] avatar = blob.getBytes(1, (int) blob.length());
        String avatarBase64 = Base64.getEncoder().encodeToString(avatar);
        return new User(id, login, role, points, money, isBlocked, avatarBase64);


    }
}
