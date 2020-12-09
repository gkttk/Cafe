package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll() throws ServiceException;

    List<Comment> getAllByDishId(Long dishId) throws ServiceException;

}
