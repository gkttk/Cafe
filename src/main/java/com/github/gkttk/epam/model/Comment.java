package com.github.gkttk.epam.model;

import java.util.Objects;

public class Comment extends Entity {
    private final Long id;
    private final String text;

    public Comment(Long id, String text) {
        this.id = id;
        this.text = text;
    }


    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
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
        return Objects.equals(id, comment.id) &&
                Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }


    @Override
    public String toString() {
        return String.format("Comment id: %d\ntext: %s", id, text);
    }
}
