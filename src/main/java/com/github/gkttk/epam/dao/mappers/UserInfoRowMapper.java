package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoRowMapper implements RowMapper<UserInfo> {
    @Override
    public UserInfo map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String roleName = resultSet.getString("role");
        UserRole role = UserRole.valueOf(roleName);
        int points = resultSet.getInt("points");
        boolean isBlocked = resultSet.getBoolean("blocked");


        return new UserInfo(id, login, role, points, isBlocked);
    }
}
