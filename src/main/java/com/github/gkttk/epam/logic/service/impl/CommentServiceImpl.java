package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.dto.CommentInfoDao;
import com.github.gkttk.epam.dao.entity.CommentDao;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.builder.CommentBuilder;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.Comment;
import com.github.gkttk.epam.model.enums.CommentSortTypes;

import java.util.List;
import java.util.Optional;

public class CommentServiceImpl implements CommentService {


    private final static int DEFAULT_LIMIT_ON_PAGE = 5;
    private final DaoHelperFactory daoHelperFactory;

    public CommentServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }


    @Override
    public Optional<CommentInfo> getById(Long commentId) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            CommentInfoDao commentDao = daoHelperImpl.createCommentInfoDao();
            return commentDao.findByCommentId(commentId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getById() with commentId: %d",
                    commentId), e);
        }
    }

    @Override
    public Long addComment(String text, Long userId, Long dishId) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            Comment comment = new Comment(null, text, userId, dishId);
            CommentDao commentDao = daoHelperImpl.createCommentDao();
            return commentDao.save(comment);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't addComment() with userId: %d, dishId: %d",
                    userId, dishId), e);
        }
    }

    @Override
    public List<CommentInfo> getAllByDishIdPagination(long dishId, int currentPage, CommentSortTypes sortType) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            CommentInfoDao commentInfoDao = daoHelperImpl.createCommentInfoDao();

            int offset = (currentPage - 1) * DEFAULT_LIMIT_ON_PAGE;
            return sortType.getComments(commentInfoDao, dishId, DEFAULT_LIMIT_ON_PAGE, offset);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getAllByDishIdPagination(dishId, currentPage) with dishId: %d," +
                    " currentPage: %d, sortType: %s", dishId, currentPage, sortType.name()), e);
        }
    }

    @Override
    public int getPageCount(long dishId) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelperImpl.createCommentDao();
            int commentCount = commentDao.rowCountForDishId(dishId);
            return (int) Math.ceil(((double) commentCount / DEFAULT_LIMIT_ON_PAGE));
        } catch (DaoException e) {
            throw new ServiceException("Can't getCommentsCount()", e);
        }
    }

    @Override
    public void updateComment(long commentId, String newCommentText) throws ServiceException {

        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelperImpl.createCommentDao();
            Optional<Comment> commentOpt = commentDao.findById(commentId);
            if (commentOpt.isPresent()) {
                Comment comment = commentOpt.get();
                CommentBuilder builder = comment.builder();
                builder.setText(newCommentText);
                Comment newComment = builder.build();
                commentDao.save(newComment);
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't updateComment(commentId, newComment text) with " +
                    "commentId: %d, newCommentText: %s", commentId, newCommentText), e);
        }


    }

    @Override
    public void removeComment(long commentId) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            CommentDao commentDao = daoHelperImpl.createCommentDao();
            commentDao.removeById(commentId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't removeComment(commentId) with " +
                    "commentId: %d", commentId), e);
        }

    }

}
