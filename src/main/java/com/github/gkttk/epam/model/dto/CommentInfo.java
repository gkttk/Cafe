package com.github.gkttk.epam.model.dto;

import com.github.gkttk.epam.model.entities.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentInfo extends Entity {
    private final String text;
    private final int rating;
    private final LocalDateTime creationDate;
    private final String userLogin;
    private final String userImageRef;


    public CommentInfo(Long commentId, String text, int rating, LocalDateTime creationDate, String userLogin, String userImageRef) {
        super(commentId);
        this.text = text;
        this.rating = rating;
        this.creationDate = creationDate;
        this.userLogin = userLogin;
        this.userImageRef = userImageRef;
    }


    public Long getId() {
        return super.getId();
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserImageRef() {
        return userImageRef;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentInfo that = (CommentInfo) o;
        return rating == that.rating &&
                Objects.equals(text, that.text) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(userLogin, that.userLogin) &&
                Objects.equals(userImageRef, that.userImageRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, rating, creationDate, userLogin, userImageRef);
    }
}