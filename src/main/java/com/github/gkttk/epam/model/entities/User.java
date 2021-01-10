package com.github.gkttk.epam.model.entities;

import com.github.gkttk.epam.model.builder.UserBuilder;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.util.Objects;

public class User extends Entity {


    private static final String DEFAULT_AVATAR_BASE64 = "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";

    private final String login;
    private final String password;
    private final UserRole role;
    private final int points;
    private final BigDecimal money;
    private final boolean blocked;
    private final String imgBase64;

    public User(String login, String password) {
        super(null);
        this.login = login;
        this.password = password;
        this.role = UserRole.USER;
        this.points = 20;
        this.money = new BigDecimal(0);
        this.blocked = false;
        this.imgBase64 = DEFAULT_AVATAR_BASE64;
    }

    public User(Long id, String login, String password, UserRole role, int points, BigDecimal money, boolean blocked, String imgBase64) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
        this.points = points;
        this.money = money;
        this.blocked = blocked;
        if (imgBase64 != null) {
            this.imgBase64 = imgBase64;
        } else {
            this.imgBase64 = DEFAULT_AVATAR_BASE64;
        }

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

    public boolean isBlocked() {
        return blocked;
    }

    public String getImgBase64() {
        return imgBase64;
    }


    public UserBuilder builder() {
        return new UserBuilder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        User user = (User) o;
        return points == user.points &&
                blocked == user.blocked &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(money, user.money) &&
                Objects.equals(imgBase64, user.imgBase64);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, role, points, money, blocked, imgBase64);
    }

    @Override
    public String toString() {
        return String.format("User id: %d, login: %s, password: %s, role: %s, points: %d, money: %f, is blocked: %b",
                getId(), login, password, role.name(), points, money, blocked);
    }
}
