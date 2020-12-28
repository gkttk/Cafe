package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.CommentDao;
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


    private boolean checkCommentWasEvaluated(Long userId, Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
            Optional<UserCommentRating> evaluateOpt = userCommentRatingDao.getByUserIdAndCommentId(userId, commentId);
            return evaluateOpt.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't checkCommentWasEvaluated with userId: %d, commentId: %d",
                    userId, commentId), e);
        }

    }

    public void remove(Long userId, Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
            userCommentRatingDao.removeByUserIdAndCommentId(userId, commentId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't remove with userId: %d, commentId: %d",
                    userId, commentId), e);
        }
    }

    //todo catch in finally
    @Override
    public void evaluateComment(long userId, long commentId, int commentRating, boolean isLiked) throws ServiceException {

        boolean wasEvaluated = checkCommentWasEvaluated(userId, commentId);

        if (wasEvaluated) {
            remove(userId, commentId);
        } else {
            int newRating = isLiked ? ++commentRating : --commentRating;
            DaoHelper daoHelper = DaoHelperFactory.createDaoHelper();
            try {
                UserCommentRatingDao userCommentRatingDao = daoHelper.createUserCommentRatingDao();
                CommentDao commentDao = daoHelper.createCommentDao();
                daoHelper.startTransaction();
                UserCommentRating userCommentRating = new UserCommentRating(userId, commentId, isLiked);
                userCommentRatingDao.save(userCommentRating);
                commentDao.updateRating(newRating, commentId);
                daoHelper.commit();
            } catch (DaoException e) {
                try {
                    daoHelper.rollback();
                } catch (DaoException ex) {
                    throw new ServiceException(String.format("Can't rollback() in evaluateComment(userId, commentId, " +
                            "commentRating, isLiked) with userId: %d, commentId: %d, commentRating: %d, " +
                            "isLiked: %b", userId, commentId, commentRating, isLiked), ex);
                }
            } finally {
                try {
                    daoHelper.endTransaction();
                    daoHelper.close();
                } catch (DaoException exception) {
                    throw new ServiceException(String.format("Can't endTransaction() in evaluateComment(userId, commentId, " +
                            "commentRating, isLiked) with userId: %d, commentId: %d, commentRating: %d, " +
                            "isLiked: %b", userId, commentId, commentRating, isLiked), exception);
                }

            }
        }


    }


}

