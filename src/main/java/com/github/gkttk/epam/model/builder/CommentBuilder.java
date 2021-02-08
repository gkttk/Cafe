package com.github.gkttk.epam.model.builder;

import com.github.gkttk.epam.model.entities.Comment;

import java.time.LocalDateTime;

public class CommentBuilder implements Builder<Comment> {

    private final Long id;
    private String text;
    private int rating;
    private LocalDateTime creationDate;
    private final long userId;
    private final long dishId;


    public CommentBuilder(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.rating = comment.getRating();
        this.creationDate = comment.getCreationDate();
        this.userId = comment.getUserId();
        this.dishId = comment.getDishId();
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

    @Override
    public Comment build() {
        return new Comment(
                this.id,
                this.text,
                this.rating,
                this.creationDate,
                this.userId,
                this.dishId
        );
    }
}
