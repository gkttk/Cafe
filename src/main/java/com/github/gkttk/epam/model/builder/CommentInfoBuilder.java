package com.github.gkttk.epam.model.builder;

import com.github.gkttk.epam.model.dto.CommentInfo;

import java.time.LocalDateTime;

public class CommentInfoBuilder implements Builder<CommentInfo> {

    private final Long id;
    private String text;
    private int rating;
    private LocalDateTime creationDate;
    private String userLogin;
    private String userAvatarBase64;

    public CommentInfoBuilder(CommentInfo commentInfo) {
        this.id = commentInfo.getId();
        this.text = commentInfo.getText();
        this.rating = commentInfo.getRating();
        this.creationDate = commentInfo.getCreationDate();
        this.userLogin = commentInfo.getUserLogin();
        this.userAvatarBase64 = commentInfo.getUserAvatarBase64();

    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setUserAvatarBase64(String userAvatarBase64) {
        this.userAvatarBase64 = userAvatarBase64;
    }

    @Override
    public CommentInfo build() {
        return new CommentInfo(
                this.id,
                this.text,
                this.rating,
                this.creationDate,
                this.userLogin,
                this.userAvatarBase64
        );
    }
}
