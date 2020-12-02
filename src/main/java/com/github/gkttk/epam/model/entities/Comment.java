package com.github.gkttk.epam.model.entities;

import java.util.Objects;

public class Comment extends Entity {
    private final String text;

    public Comment(Long id, String text) {
        super(id);
        this.text = text;
    }


    public Long getId() {
        return super.getId();
    }

    public String getText() {
        return text;
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
        return Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return String.format("Comment id: %d\ntext: %s", getId(), text);
    }
}
