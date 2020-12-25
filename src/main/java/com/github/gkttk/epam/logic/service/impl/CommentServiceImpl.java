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


    private final static int DEFAULT_LIMIT_ON_PAGE = 5;


    @Override
    public List<Comment> getAll() throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            return commentDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getAll()", e);
        }
    }



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

    @Override
    public List<CommentInfo> getAllByDishIdPagination(long dishId, int currentPage) throws ServiceException {

        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentInfoDao commentInfoDao = daoHelper.createCommentInfoDao();
            CommentDao commentDao = daoHelper.createCommentDao();

            int commentsCount = commentDao.rowCountForDishId(dishId);
            int offset = (currentPage - 1) * DEFAULT_LIMIT_ON_PAGE;
            int limit = DEFAULT_LIMIT_ON_PAGE;

            if(commentsCount - offset < limit){
                limit = commentsCount - offset;
            }

            return commentInfoDao.findAllByDishIdPagination(dishId, limit, offset);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllByDishIdPagination(dishId, currentPage) with dishId: %d," +
                    " currentPage: %d", dishId, currentPage), e);
        }
    }

    @Override
    public int getPageCount(long dishId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            int commentCount = commentDao.rowCountForDishId(dishId);
            return (int)Math.ceil(((double)commentCount / DEFAULT_LIMIT_ON_PAGE));
        } catch (DaoException e) {
            throw new ServiceException("Can't getCommentsCount()", e);
        }
    }


}
