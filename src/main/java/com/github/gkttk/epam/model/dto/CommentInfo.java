package com.github.gkttk.epam.model.dto;

import com.github.gkttk.epam.model.builder.CommentInfoBuilder;
import com.github.gkttk.epam.model.builder.OrderBuilder;
import com.github.gkttk.epam.model.entities.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentInfo extends Entity {
    private final String text;
    private final int rating;
    private final LocalDateTime creationDate;
    private final String userLogin;
    private final String userAvatarBase64;


    public CommentInfo(Long commentId, String text, int rating, LocalDateTime creationDate, String userLogin, String userAvatarBase64) {
        super(commentId);
        this.text = text;
        this.rating = rating;
        this.creationDate = creationDate;
        this.userLogin = userLogin;
        this.userAvatarBase64 = userAvatarBase64;
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

    public String getUserAvatarBase64() {
        return userAvatarBase64;
    }



    public CommentInfoBuilder builder() {
        return new CommentInfoBuilder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        CommentInfo that = (CommentInfo) o;
        return rating == that.rating &&
                Objects.equals(text, that.text) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(userLogin, that.userLogin) &&
                Objects.equals(userAvatarBase64, that.userAvatarBase64);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, rating, creationDate, userLogin, userAvatarBase64);
    }
}
