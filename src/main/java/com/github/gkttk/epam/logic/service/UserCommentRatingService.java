package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.List;

public interface UserCommentRatingService {

    List<UserCommentRating> getAllByUserIdAndDishId(long userId, long dishId) throws ServiceException;

    void rateComment(long userId, long commentId, boolean isLiked) throws ServiceException;

}
