package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.UserCommentRatingDao;
import com.github.gkttk.epam.dao.extractors.UserCommentRatingFieldExtractor;
import com.github.gkttk.epam.dao.mappers.UserCommentRatingRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserCommentRatingDaoImpl extends AbstractDao<UserCommentRating> implements UserCommentRatingDao {

    private final static String TABLE_NAME = "users_comments_rating";

    private final static String REMOVE_BY_USER_ID_AND_COMMENT_ID_QUERY = "DELETE FROM " + TABLE_NAME +
            " WHERE user_id = ? AND comment_id = ?";

    private final static String SELECT_BY_USER_ID_AND_COMMENT_ID_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE user_id = ? AND comment_id = ?";

    private final static String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?)";

    private final static String SELECT_BY_USER_ID_AND_DISH_ID_QUERY = "SELECT uc.user_id, uc.comment_id, uc.liked" +
            " from users_comments_rating AS uc JOIN comments AS c on uc.comment_id = c.id WHERE uc.user_id = ? AND c.dish_id = ?";


    public UserCommentRatingDaoImpl(Connection connection) {
        super(connection, new UserCommentRatingRowMapper(), new UserCommentRatingFieldExtractor());
    }

    @Override
    public List<UserCommentRating> findAllByUserIdAndDishId(long userId, long dishId) throws DaoException {
        return getAllResults(SELECT_BY_USER_ID_AND_DISH_ID_QUERY, userId, dishId);
    }

    @Override
    public void removeByUserIdAndCommentId(long userId, long commentId) throws DaoException {
        try (PreparedStatement statement = createPrepareStatement(REMOVE_BY_USER_ID_AND_COMMENT_ID_QUERY,
                userId, commentId)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't remove with userId: %d, commentId: %d",
                    userId, commentId), e);
        }
    }

    @Override
    public Optional<UserCommentRating> getByUserIdAndCommentId(long userId, long commentId) throws DaoException {
        return getSingleResult(SELECT_BY_USER_ID_AND_COMMENT_ID_QUERY, userId, commentId);
    }

    @Override
    public long save(UserCommentRating entity) throws DaoException {

        Long userId = entity.getUserId();
        Long commentId = entity.getCommentId();
        boolean isLiked = entity.isLiked();

        try (PreparedStatement statement = createPrepareStatement(INSERT_QUERY, userId, commentId, isLiked)) {
            statement.executeUpdate();
            return userId;

        } catch (SQLException e) {
            throw new DaoException("Can't save with entity: " + entity.toString(), e);
        }
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
