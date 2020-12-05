package com.github.gkttk.epam.model.entities;

import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.util.Objects;

public class User extends Entity {

    private final String login;
    private final String password;
    private final UserRole role;
    private final int points;
    private final BigDecimal money;
    private final boolean active;

    public User(String login, String password){
        super(null);
        this.login = login;
        this.password = password;
        this.role = UserRole.USER;
        this.points = 20;
        this.money = new BigDecimal(0);
        this.active = true;

    }

    public User(Long id, String login, String password, UserRole role, int points, BigDecimal money, boolean active) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
        this.points = points;
        this.money = money;
        this.active = active;
    }

    public Long getId() {
        return super.getId();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public int getPoints() {
        return points;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public boolean isActive() {
        return active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return points == user.points &&
                active == user.active &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(money, user.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, role, points, money, active);
    }

    @Override
    public String toString() {
        return String.format("User id: %d, login: %s, password: %s, role: %s, points: %d, money: %f, is active: %b",
                getId(), login, password, role.toString(), points, money, active);
    }
}
