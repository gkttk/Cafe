package com.github.gkttk.epam.dao.parsers;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class UserParser implements EntityParser<User> {
    @Override
    public List<Object> parse(User user) {

        Long id = user.getId();
        String login = user.getLogin();
        String password = user.getPassword();
        UserRole role = user.getRole();
        String roleName = role.toString();

        int points = user.getPoints();
        BigDecimal money = user.getMoney();
        boolean active = user.isActive();

       return Arrays.asList(id, login, password, roleName, points, money, active);

    }
}
