package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.CommentDao;
import com.github.gkttk.epam.dao.extractors.CommentFieldExtractor;
import com.github.gkttk.epam.dao.mappers.CommentRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Comment;

import java.sql.Connection;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {

    private final static String TABLE_NAME = "comments";
    private final static String ROW_COUNT_BY_DISH_ID_QUERY = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE dish_id = ?";

    public CommentDaoImpl(Connection connection) {
        super(connection, new CommentRowMapper(), new CommentFieldExtractor());
    }

    @Override
    public int rowCountByDishId(long dishId) throws DaoException {
        return rowCount(ROW_COUNT_BY_DISH_ID_QUERY, dishId);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
