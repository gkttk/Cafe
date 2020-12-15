package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.dto.CommentInfoDao;
import com.github.gkttk.epam.dao.dto.impl.CommentInfoDaoImpl;
import com.github.gkttk.epam.dao.entity.CommentDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.dto.CommentInfo;
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

  /*  @Override
    public List<Comment> getAllByDishId(Long dishId) throws ServiceException {
        List<Comment> comments = new ArrayList<>();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            comments = commentDao.findAllByDishId(dishId);
        } catch (DaoException e) {
            throw new ServiceException("Can't getAll()", e);
        }

        return comments;
    }*/

    @Override
    public List<CommentInfo> getAllByDishId(Long dishId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentInfoDao commentInfoDao = daoHelper.createCommentInfoDao();
            return commentInfoDao.findAllByDishId(dishId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllByDishId() with dishId: %d", dishId),e);
        }
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
    public Optional<CommentInfo> getById(Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentInfoDao commentDao = daoHelper.createCommentInfoDao();
            return commentDao.findByCommentId(commentId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getById() with commentId: %d",
                    commentId), e);
        }
    }

    @Override
    public Long addComment(String text, Long userId, Long dishId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            Comment comment = new Comment(null, text, userId, dishId);
            CommentDao commentDao = daoHelper.createCommentDao();
            return commentDao.save(comment);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't addComment() with userId: %d, dishId: %d",
                    userId, dishId), e);
        }
    }


}
