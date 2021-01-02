package com.github.gkttk.epam.model.enums;

import com.github.gkttk.epam.dao.dto.CommentInfoDao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.dto.CommentInfo;

import java.util.List;

public enum SortTypes {
    DATE {
        public List<CommentInfo> getComments(CommentInfoDao commentInfoDao, long dishId, int limit, int offset) throws DaoException {
            return commentInfoDao.findAllByDishIdOrderDatePagination(dishId, limit, offset);
        }
    }, RATING {
        public List<CommentInfo> getComments(CommentInfoDao commentInfoDao, long dishId, int limit, int offset) throws DaoException {
            return commentInfoDao.findAllByDishIdOrderRatingPagination(dishId, limit, offset);
        }
    };


    public abstract List<CommentInfo> getComments(CommentInfoDao commentInfoDao, long dishId, int limit, int offset) throws DaoException;
}
