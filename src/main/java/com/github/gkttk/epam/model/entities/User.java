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
    private final boolean blocked;
    private final String imageRef;

    public User(String login, String password){
        super(null);
        this.login = login;
        this.password = password;
        this.role = UserRole.USER;
        this.points = 20;
        this.money = new BigDecimal(0);
        this.blocked = false;
        this.imageRef = "static/images/users/not_found.jpg";//todo common
    }

    public User(Long id, String login, String password, UserRole role, int points, BigDecimal money, boolean blocked, String imageRef) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
        this.points = points;
        this.money = money;
        this.blocked = blocked;
        this.imageRef = imageRef;
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

    public String getImageRef() {
        return imageRef;
    }


    public User changeActive(boolean newStatus){
        return new User(
                this.getId(),
                this.login,
                this.password,
                this.role,
                this.points,
                this.money,
                newStatus,
                this.imageRef
        );
    }

    public User changeImageRef(String newImageRef) {
        return new User(
                this.getId(),
                this.login,
                this.password,
                this.role,
                this.points,
                this.money,
                this.blocked,
                newImageRef
        );
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
                Objects.equals(imageRef, user.imageRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, role, points, money, blocked, imageRef);
    }

    @Override
    public String toString() {
        return String.format("User id: %d, login: %s, password: %s, role: %s, points: %d, money: %f, is blocked: %b",
                getId(), login, password, role.name(), points, money, blocked);
    }
}
