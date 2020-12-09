package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Comment;

import java.util.List;

public interface CommentDao extends Dao<Comment> {

    List<Comment> findAllByDishId(Long dishId) throws DaoException;

}
