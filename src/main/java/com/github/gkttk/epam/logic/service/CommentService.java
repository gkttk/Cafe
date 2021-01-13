package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.enums.CommentSortTypes;

import java.util.List;
import java.util.Optional;

public interface CommentService {


    Optional<CommentInfo> getById(Long commentId) throws ServiceException;

    Long addComment(String text, Long userId, Long dishId) throws ServiceException;

    List<CommentInfo> getAllByDishIdPagination(long dishId, int currentPage, CommentSortTypes sortType) throws ServiceException;

    int getPageCount(long dishId) throws ServiceException;

    void updateComment(long commentId, String newCommentText) throws ServiceException;

    void removeComment(long commentId) throws ServiceException;


}
