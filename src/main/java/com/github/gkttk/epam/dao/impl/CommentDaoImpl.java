package com.github.gkttk.epam.dao.impl;

import com.github.gkttk.epam.dao.AbstractDao;
import com.github.gkttk.epam.dao.CommentDao;
import com.github.gkttk.epam.dao.extractors.CommentFieldExtractor;
import com.github.gkttk.epam.dao.mappers.CommentRowMapper;
import com.github.gkttk.epam.model.entities.Comment;

import java.sql.Connection;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {

    private final static String TABLE_NAME = "comment";

    public CommentDaoImpl(Connection connection) {
        super(connection, new CommentRowMapper(), new CommentFieldExtractor());
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
