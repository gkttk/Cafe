package com.github.gkttk.epam.model.dto;

import com.github.gkttk.epam.model.builder.UserInfoBuilder;
import com.github.gkttk.epam.model.entities.Entity;
import com.github.gkttk.epam.model.enums.UserRole;

import java.util.Objects;

public class UserInfo extends Entity {

    private final String login;
    private final UserRole role;
    private final int points;
    private final boolean blocked;


    public UserInfo(Long id, String login, UserRole role, int points, boolean blocked) {
        super(id);
        this.login = login;
        this.role = role;
        this.points = points;
        this.blocked = blocked;
    }

    public UserInfoBuilder builder() {
        return new UserInfoBuilder(this);
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public int getPoints() {
        return points;
    }


    public boolean isBlocked() {
        return blocked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return (this.getId() == null || super.getId().equals(userInfo.getId())) &&
                points == userInfo.points &&
                blocked == userInfo.blocked &&
                Objects.equals(login, userInfo.login) &&
                role == userInfo.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, points, role, blocked);
    }


    @Override
    public String toString() {
        return String.format("UserInfo: login - %s, points - %d, role - %s, blocked - %b",
                login, points, role, blocked);
    }
}
