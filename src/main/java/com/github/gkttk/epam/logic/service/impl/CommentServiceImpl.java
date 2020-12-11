package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.CommentDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.entities.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentServiceImpl implements CommentService {


    @Override
    public List<Comment> getAll() throws ServiceException {
        List<Comment> comments = new ArrayList<>();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            comments = commentDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getAll()", e);
        }

        return comments;
    }

    @Override
    public List<Comment> getAllByDishId(Long dishId) throws ServiceException {
        List<Comment> comments = new ArrayList<>();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            comments = commentDao.findAllByDishId(dishId);
        } catch (DaoException e) {
            throw new ServiceException("Can't getAll()", e);
        }

        return comments;
    }


    @Override
    public void changeCommentRating(int newRating, Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            commentDao.updateRating(newRating, commentId);

        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't rollback() in changeCommentRating with newRating: %d, commentId: %d",
                    newRating, commentId), e);
        }
    }

    @Override
    public Optional<Comment> getById(Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            return commentDao.getById(commentId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getById() with commentId: %d",
                    commentId), e);
        }
    }


}
