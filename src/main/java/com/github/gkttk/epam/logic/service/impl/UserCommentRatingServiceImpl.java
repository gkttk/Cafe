package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.CommentDao;
import com.github.gkttk.epam.dao.entity.UserCommentRatingDao;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.model.builder.CommentBuilder;
import com.github.gkttk.epam.model.entities.Comment;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserCommentRatingServiceImpl implements UserCommentRatingService {

    private final static Logger LOGGER = LogManager.getLogger(UserCommentRatingServiceImpl.class);
    private final DaoHelperFactory daoHelperFactory;

    public UserCommentRatingServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public List<UserCommentRating> getAllByUserIdAndDishId(Long userId, Long dishId) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserCommentRatingDao userCommentRatingDao = daoHelperImpl.createUserCommentRatingDao();
            return userCommentRatingDao.findAllByUserIdAndDishId(userId, dishId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllByUserIdAndDishId() with userId: %d, dishId: %d",
                    userId, dishId), e);
        }
    }

    @Override
    public void evaluateComment(long userId, long commentId, boolean isLiked) throws ServiceException {
        DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper();
        try {
            UserCommentRatingDao userCommentRatingDao = daoHelperImpl.createUserCommentRatingDao();
            CommentDao commentDao = daoHelperImpl.createCommentDao();

            daoHelperImpl.startTransaction();

            boolean wasEvaluated = checkCommentWasEvaluated(userCommentRatingDao, userId, commentId);
            if (wasEvaluated) {
                remove(userCommentRatingDao, userId, commentId);
            } else {
                UserCommentRating userCommentRating = new UserCommentRating(userId, commentId, isLiked);
                userCommentRatingDao.save(userCommentRating);
            }

            Optional<Comment> currentCommentOpt = commentDao.findById(commentId);
            if (currentCommentOpt.isPresent()) {
                Comment comment = currentCommentOpt.get();
                int currentRating = comment.getRating();

                int newRating = isLiked ? ++currentRating : --currentRating;

                CommentBuilder builder = comment.builder();
                builder.setRating(newRating);
                Comment newComment = builder.build();
                commentDao.save(newComment);
            }

            daoHelperImpl.commit();
        } catch (DaoException e) {
            try {
                daoHelperImpl.rollback();
            } catch (DaoException ex) {
                throw new ServiceException(String.format("Can't rollback() in evaluateComment(userId, commentId, " +
                                "commentRating, isLiked) with userId: %d, commentId: %d, isLiked: %b",
                        userId, commentId, isLiked), ex);
            }
            throw new ServiceException(String.format("Can't evaluateComment(userId, commentId, isLiked) " +
                    " with userId: %d, commentId: %d, isLiked: %b", userId, commentId, isLiked), e);
        } finally {
            try {
                daoHelperImpl.endTransaction();
            } catch (DaoException exception) {
                LOGGER.warn("Can't endTransaction() in evaluateComment(userId,commentId,commentRating, isLiked) " +
                        "with userId: {}, commentRating: {}, isLiked: {}", userId, commentId, isLiked, exception);
            }
            daoHelperImpl.close();

        }
    }

    private boolean checkCommentWasEvaluated(UserCommentRatingDao userCommentRatingDao, long userId, long commentId) throws ServiceException {
        try {
            Optional<UserCommentRating> evaluateOpt = userCommentRatingDao.getByUserIdAndCommentId(userId, commentId);
            return evaluateOpt.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't checkCommentWasEvaluated with userId: %d, commentId: %d",
                    userId, commentId), e);
        }
    }

    private void remove(UserCommentRatingDao userCommentRatingDao, long userId, long commentId) throws ServiceException {
        try {
            userCommentRatingDao.removeByUserIdAndCommentId(userId, commentId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't remove with userId: %d, commentId: %d",
                    userId, commentId), e);
        }
    }


}

