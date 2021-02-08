package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoRowMapper implements RowMapper<UserInfo> {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String POINTS_KEY = "points";
    private final static String ROLE_KEY = "role";
    private final static String BLOCKED_KEY = "blocked";

    @Override
    public UserInfo map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_KEY);

        String login = resultSet.getString(LOGIN_KEY);

        String roleName = resultSet.getString(ROLE_KEY);
        UserRole role = UserRole.valueOf(roleName);

        int points = resultSet.getInt(POINTS_KEY);

        boolean isBlocked = resultSet.getBoolean(BLOCKED_KEY);

        return new UserInfo(id, login, role, points, isBlocked);
    }
}
