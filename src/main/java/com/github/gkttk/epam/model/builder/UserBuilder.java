package com.github.gkttk.epam.model.builder;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;

public class UserBuilder implements Builder<User> {

    private final Long id; //immutable even here
    private String login;
    private String password;
    private UserRole role;
    private int points;
    private BigDecimal money;
    private boolean blocked;
    private String imageRef;


    public UserBuilder(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.points = user.getPoints();
        this.money = user.getMoney();
        this.blocked = user.isBlocked();
        this.imageRef = user.getImageRef();
    }


    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
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
                this.password,
                this.role,
                this.points,
                this.money,
                this.blocked,
                this.imageRef
        );
    }
}
