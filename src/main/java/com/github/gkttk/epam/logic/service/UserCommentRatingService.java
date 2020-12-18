package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentEstimate;

import java.util.List;
import java.util.Optional;

public interface UserCommentRatingService {


    List<UserCommentRating> getAllByUserIdAndDishId(Long userId, Long dishId) throws ServiceException;

    List<UserCommentRating> getAllByUserId(Long userId) throws ServiceException;

    boolean checkCommentWasEvaluated(Long userId, Long commentId) throws ServiceException;

    void remove(Long userId, Long commentId) throws ServiceException;

    void evaluateComment(Long userId, Long commentId, CommentEstimate estimate) throws ServiceException;

}