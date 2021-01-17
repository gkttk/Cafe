package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.enums.CommentSortType;

import java.util.List;
import java.util.Optional;

public interface CommentService {


    Optional<CommentInfo> getById(long commentId) throws ServiceException;

    long addComment(String text, long userId, long dishId) throws ServiceException;

    List<CommentInfo> getAllByDishIdPagination(long dishId, int currentPage, CommentSortType sortType) throws ServiceException;

    int getPageCount(long dishId) throws ServiceException;

    void updateComment(long commentId, String newCommentText) throws ServiceException;

    void removeComment(long commentId) throws ServiceException;


}
