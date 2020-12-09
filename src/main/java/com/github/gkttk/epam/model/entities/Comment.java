package com.github.gkttk.epam.model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment extends Entity {
    private final String text;
    private final int rating;
    private final LocalDateTime creationDate;
    private final int userId;
    private final int dishId;

    public Comment(Long id, String text, int rating, LocalDateTime creationDate, int userId, int dishId) {
        super(id);
        this.text = text;
        this.rating = rating;
        this.creationDate = creationDate;
        this.userId = userId;
        this.dishId = dishId;
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

    public int getUserId() {
        return userId;
    }

    public int getDishId() {
        return dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Comment comment = (Comment) o;
        return rating == comment.rating &&
                userId == comment.userId &&
                dishId == comment.dishId &&
                Objects.equals(text, comment.text) &&
                Objects.equals(creationDate, comment.creationDate);
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
