package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.CommentDao;
import com.github.gkttk.epam.dao.extractors.CommentFieldExtractor;
import com.github.gkttk.epam.dao.mappers.CommentRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {

    private final static String TABLE_NAME = "comments";

    private final static String GET_ALL_BY_DISH_ID_QUERY = "SELECT c.id, c.text, c.rating, c.creation_date, u.login, u.image_ref" +
            " from comments as c join users as u on c.user_id = u.id where dish_id = ?";

    private final static String UPDATE_RATING_QUERY = "UPDATE " + TABLE_NAME + " SET rating = ? WHERE id = ?";

    public CommentDaoImpl(Connection connection) {
        super(connection, new CommentRowMapper(), new CommentFieldExtractor());
    }

    @Override
    public List<Comment> findAllByDishId(Long dishId) throws DaoException {
        return getAllResults(GET_ALL_BY_DISH_ID_QUERY, dishId);

    }

    @Override
    public void updateRating(int newRating, Long commentId) throws DaoException {
        try(PreparedStatement preparedStatement = createPrepareStatement(UPDATE_RATING_QUERY,
                newRating, commentId)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
          throw new DaoException(String.format("Can't updateRating with newRating: %d, commentId: %d",
                  newRating, commentId),e);
        }
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }



}
