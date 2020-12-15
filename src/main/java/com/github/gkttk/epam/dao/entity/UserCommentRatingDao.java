package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentEstimate;

import java.util.List;
import java.util.Optional;

public interface UserCommentRatingDao extends Dao<UserCommentRating> {


    List<UserCommentRating> findAllByUserIdAndDishId(Long userId, Long dishId) throws DaoException;

    List<UserCommentRating> findAllByUserId(Long userId) throws DaoException;

    void updateEstimate(CommentEstimate estimate, Long commentId) throws DaoException;

    void removeByUserIdAndCommentId(Long userId, Long comment_id) throws DaoException;

    Optional<UserCommentRating> getByUserIdAndCommentId(Long userId, Long commentId) throws DaoException;

    List<UserCommentRating> findAllByCommentId(Long commentId) throws DaoException;





}
