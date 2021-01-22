package com.github.gkttk.epam.model.builder;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;

public class UserBuilder implements Builder<User> {

    private final Long id;
    private String login;
    private UserRole role;
    private int points;
    private BigDecimal money;
    private boolean blocked;
    private String imageRef;


    public UserBuilder(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.role = user.getRole();
        this.points = user.getPoints();
        this.money = user.getMoney();
        this.blocked = user.isBlocked();
        this.imageRef = user.getImgBase64();
    }


    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }


    public UserBuilder setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserBuilder setPoints(int points) {
        this.points = points;
        return this;
    }

    public UserBuilder setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public UserBuilder setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public UserBuilder setImageRef(String imageRef) {
        this.imageRef = imageRef;
        return this;
    }

    @Override
    public User build() {
        return new User(
                this.id,
                this.login,
                this.role,
                this.points,
                this.money,
                this.blocked,
                this.imageRef
        );
    }
}
