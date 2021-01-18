package com.github.gkttk.epam.dao.dto;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.dto.CommentInfo;

import java.util.List;
import java.util.Optional;


/**
 * Dao for working with CommendInfo entity.
 */
public interface CommentInfoDao extends Dao<CommentInfo> {

    Optional<CommentInfo> findByCommentId(long commentId) throws DaoException;

    List<CommentInfo> findAllByDishIdSortByOrderPagination(long dishId, int limit, int offset) throws DaoException;

    List<CommentInfo> findAllByDishIdSortByDatePagination(long dishId, int limit, int offset) throws DaoException;

}
