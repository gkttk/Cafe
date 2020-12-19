package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.List;

public interface UserCommentRatingService {


    List<UserCommentRating> getAllByUserIdAndDishId(Long userId, Long dishId) throws ServiceException;

    List<UserCommentRating> getAllByUserId(Long userId) throws ServiceException;

    boolean checkCommentWasEvaluated(Long userId, Long commentId) throws ServiceException;

    void remove(Long userId, Long commentId) throws ServiceException;

    void evaluateComment(Long userId, Long commentId, boolean isLiked) throws ServiceException;

}
