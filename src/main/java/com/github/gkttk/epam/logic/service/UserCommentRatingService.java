package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.List;

public interface UserCommentRatingService {


    List<UserCommentRating> getAllByUserIdAndDishId(Long userId, Long dishId) throws ServiceException;

    void evaluateComment(long userId, long commentId, boolean isLiked) throws ServiceException;

}
