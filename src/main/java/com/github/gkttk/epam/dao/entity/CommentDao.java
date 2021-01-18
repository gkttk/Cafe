package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Comment;

/**
 * Dao for working with Comment entity.
 */
public interface CommentDao extends Dao<Comment> {

    int rowCountByDishId(long dishId) throws DaoException;


}
