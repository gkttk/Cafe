package com.github.gkttk.epam.model.builder;

import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserRole;

public class UserInfoBuilder implements Builder<UserInfo> {

    private final Long id;
    private String login;
    private UserRole role;
    private int points;
    private boolean blocked;

    public UserInfoBuilder(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.login = userInfo.getLogin();
        this.role = userInfo.getRole();
        this.points = userInfo.getPoints();
        this.blocked = userInfo.isBlocked();
    }

    @Override
    public UserInfo build() {
        return new UserInfo(
                this.id,
                this.login,
                this.role,
                this.points,
                this.blocked
        );
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    public void setPoints(int points) {
        this.points = points;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }



}
