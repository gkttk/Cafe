package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String roleName = resultSet.getString("role");
        UserRole role = UserRole.valueOf(roleName);
        int points = resultSet.getInt("points");
        BigDecimal money = resultSet.getBigDecimal("money");
        boolean active = resultSet.getBoolean("active");

        String imageRef = resultSet.getString("image_ref");

        return new User(id, login, password, role, points, money, active, imageRef);
    }
}
