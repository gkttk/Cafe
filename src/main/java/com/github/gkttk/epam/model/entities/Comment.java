package com.github.gkttk.epam.model.entities;

import com.github.gkttk.epam.model.builder.CommentBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment extends Entity {
    private final String text;
    private final int rating;
    private final LocalDateTime creationDate;
    private final Long userId;
    private final Long dishId;


    public Comment(Long id, String text, Long userId, Long dishId) {
        super(id);
        this.text = text;
        this.userId = userId;
        this.dishId = dishId;
        this.rating = 0;
        this.creationDate = null;
    }

    public Comment(Long id, String text, int rating, LocalDateTime creationDate, Long userId, Long dishId) {
        super(id);
        this.text = text;
        this.rating = rating;
        this.creationDate = creationDate;
        this.userId = userId;
        this.dishId = dishId;
    }


    public CommentBuilder builder() {
        return new CommentBuilder(this);
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

    public Long getUserId() {
        return userId;
    }

    public Long getDishId() {
        return dishId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;

        return (this.getId() == null || super.getId().equals(comment.getId())) &&
                rating == comment.rating &&
                Objects.equals(text, comment.text) &&
                Objects.equals(creationDate, comment.creationDate) &&
                Objects.equals(userId, comment.userId) &&
                Objects.equals(dishId, comment.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, rating, creationDate, userId, dishId);
    }


    @Override
    public String toString() {
        return String.format("Comment id: %d\ntext: %s", getId(), text);
    }
}
