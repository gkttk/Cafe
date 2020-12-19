package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.UserCommentRatingDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.List;
import java.util.Optional;

public class UserCommentRatingServiceImpl implements UserCommentRatingService {

    @Override
    public List<UserCommentRating> getAllByUserIdAndDishId(Long userId, Long dishId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
            return userCommentRatingDao.findAllByUserIdAndDishId(userId, dishId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllByUserIdAndDishId() with userId: %d, dishId: %d",
                    userId, dishId), e);
        }
    }

    @Override
    public List<UserCommentRating> getAllByUserId(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
            return userCommentRatingDao.findAllByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllByUserId with userId:%d", userId), e);
        }
    }

    @Override
    public boolean checkCommentWasEvaluated(Long userId, Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
            Optional<UserCommentRating> optional = userCommentRatingDao.getByUserIdAndCommentId(userId, commentId);
            return optional.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't checkCommentWasEvaluated with userId: %d, commentId: %d",
                    userId, commentId), e);
        }

    }

    @Override
    public void remove(Long userId, Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
            userCommentRatingDao.removeByUserIdAndCommentId(userId, commentId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't remove with userId: %d, commentId: %d",
                    userId, commentId), e);
        }
    }

    @Override
    public void evaluateComment(Long userId, Long commentId, boolean isLiked) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
            UserCommentRating userCommentRating = new UserCommentRating(userId, commentId, isLiked);
            userCommentRatingDao.save(userCommentRating);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't evaluateComment with userId: %d, commentId: %d",
                    userId, commentId), e);
        }
    }


}

