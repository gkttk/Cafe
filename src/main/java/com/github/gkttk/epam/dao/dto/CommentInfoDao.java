package com.github.gkttk.epam.dao.dto;

import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.dto.CommentInfo;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CommentInfoDao {

    List<CommentInfo> findAllByDishId(Long dishId) throws DaoException;

    Optional<CommentInfo> findByCommentId(Long commentId) throws DaoException;

}
