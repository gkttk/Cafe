package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> getAll() throws ServiceException;

    List<CommentInfo> getAllByDishId(Long dishId) throws ServiceException;

    void changeCommentRating(int newRating, Long commentId) throws ServiceException;

    Optional<CommentInfo> getById(Long commentId) throws ServiceException;

    Long addComment(String text,Long userId, Long dishId) throws ServiceException;




}
