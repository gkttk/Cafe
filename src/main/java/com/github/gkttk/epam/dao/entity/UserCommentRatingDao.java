package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.List;
import java.util.Optional;


/**
 * Dao for working with UserCommentRating entity.
 */
public interface UserCommentRatingDao extends Dao<UserCommentRating> {

    List<UserCommentRating> findAllByUserIdAndDishId(long userId, long dishId) throws DaoException;

    void removeByUserIdAndCommentId(long userId, long comment_id) throws DaoException;

    Optional<UserCommentRating> getByUserIdAndCommentId(long userId, long commentId) throws DaoException;


}
