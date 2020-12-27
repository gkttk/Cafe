package com.github.gkttk.epam.dao.dto.impl;

import com.github.gkttk.epam.dao.dto.CommentInfoDao;
import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.extractors.CommentInfoFieldExtractor;
import com.github.gkttk.epam.dao.mappers.CommentInfoRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.dto.CommentInfo;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CommentInfoDaoImpl extends AbstractDao<CommentInfo> implements CommentInfoDao {

    private final static String FIND_ALL_BY_DISH_ID_QUERY = "SELECT c.id, c.text, c.rating, c.creation_date, u.login, u.img_base64" +
            " FROM comments AS c JOIN users AS u on c.user_id = u.id where dish_id = ?";

    private final static String FIND_BY_COMMENT_ID_QUERY = "SELECT c.id, c.text, c.rating, c.creation_date, u.login," +
            " u.img_base64 from comments AS c JOIN users AS u ON c.user_id = u.id WHERE c.id = ?";

    private final static String FIND_BY_DISH_ID_PAGINATION_ORDER_RATING_QUERY = "SELECT c.id, c.text, c.rating, c.creation_date, u.login, u.img_base64 " +
            "FROM comments AS c JOIN users AS u on c.user_id = u.id WHERE dish_id = ? ORDER BY c.rating DESC LIMIT ? OFFSET ?";

    private final static String FIND_BY_DISH_ID_PAGINATION_ORDER_DATE_QUERY = "SELECT c.id, c.text, c.rating, c.creation_date, u.login, u.img_base64 " +
            "FROM comments AS c JOIN users AS u on c.user_id = u.id WHERE dish_id = ? ORDER BY c.creation_date DESC LIMIT ? OFFSET ?";


    private final static String TABLE_NAME = "user_comments_rating";




    public CommentInfoDaoImpl(Connection connection) {
        super(connection, new CommentInfoRowMapper(), new CommentInfoFieldExtractor());

    }


    @Override
    public List<CommentInfo> findAllByDishIdOrderRatingPagination(long dishId, int limit, int offset) throws DaoException {
        return getAllResults(FIND_BY_DISH_ID_PAGINATION_ORDER_RATING_QUERY, dishId, limit, offset);
    }


    @Override
    public List<CommentInfo> findAllByDishIdOrderDatePagination(long dishId, int limit, int offset) throws DaoException {
        return getAllResults(FIND_BY_DISH_ID_PAGINATION_ORDER_DATE_QUERY, dishId, limit, offset);
    }

    @Override
    public List<CommentInfo> findAllByDishId(Long dishId) throws DaoException {
        return getAllResults(FIND_ALL_BY_DISH_ID_QUERY, dishId);
    }

    @Override
    public Optional<CommentInfo> findByCommentId(Long commentId) throws DaoException {
        return getSingleResult(FIND_BY_COMMENT_ID_QUERY, commentId);
    }



    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
