package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Comment;

public interface CommentDao extends Dao<Comment> {

    void updateRating(int newRating, Long commentId) throws DaoException;

    int rowCountForDishId(long dishId) throws DaoException;


}
