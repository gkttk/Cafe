package com.github.gkttk.epam.dao.parsers;

import com.github.gkttk.epam.model.entities.Comment;

import java.util.Arrays;
import java.util.List;

public class CommentParser implements EntityParser<Comment> {
    @Override
    public List<Object> parse(Comment comment) {

        Long id = comment.getId();
        String text = comment.getText();

        return Arrays.asList(id, text);

    }
}
