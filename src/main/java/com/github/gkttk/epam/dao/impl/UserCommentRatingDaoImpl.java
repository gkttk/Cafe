package com.github.gkttk.epam.dao.impl;

import com.github.gkttk.epam.dao.AbstractDao;
import com.github.gkttk.epam.dao.UserCommentRatingDao;
import com.github.gkttk.epam.dao.extractors.UserCommentRatingFieldExtractor;
import com.github.gkttk.epam.dao.mappers.UserCommentRatingRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentEstimate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserCommentRatingDaoImpl extends AbstractDao<UserCommentRating> implements UserCommentRatingDao {

    private final static String TABLE_NAME = "users_comments_rating";
    private final static String FIND_ALL_BY_USER_ID = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
    private final static String UPDATE_ESTIMATE_QUERY = "UPDATE " + TABLE_NAME + " SET estimate = ? WHERE comment_id = ?";

    private final static String REMOVE_BY_USER_ID_AND_COMMENT_ID_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE user_id = ? AND comment_id = ?";
    private final static String SELECT_BY_USER_ID_AND_COMMENT_ID_QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? AND comment_id = ?";
    private final static String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?)";

    private final static String SELECT_BY_COMMENT_ID_QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE comment_id = ?";

    public UserCommentRatingDaoImpl(Connection connection) {
        super(connection, new UserCommentRatingRowMapper(), new UserCommentRatingFieldExtractor());
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    @Override
    public List<UserCommentRating> findAllByUserId(Long userId) throws DaoException {
        return getAllResults(FIND_ALL_BY_USER_ID, userId);
    }

    @Override
    public void updateEstimate(CommentEstimate estimate, Long commentId) throws DaoException {
        try (PreparedStatement statement = createPrepareStatement(UPDATE_ESTIMATE_QUERY, estimate.name(), commentId)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't updateEstimate with estimate: %s, commentId: %d",
                    estimate.name(), commentId), e);
        }


    }

    @Override
    public void removeByUserIdAndCommentId(Long userId, Long commentId) throws DaoException {
        try (PreparedStatement statement = createPrepareStatement(REMOVE_BY_USER_ID_AND_COMMENT_ID_QUERY,
                userId, commentId)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't remove with userId: %d, commentId: %d",
                    userId, commentId), e);
        }

    }

    @Override
    public Optional<UserCommentRating> getByUserIdAndCommentId(Long userId, Long commentId) throws DaoException {
        return getSingleResult(SELECT_BY_USER_ID_AND_COMMENT_ID_QUERY, userId, commentId);

    }

    @Override
    public List<UserCommentRating> findAllByCommentId(Long commentId) throws DaoException {
        return getAllResults(SELECT_BY_COMMENT_ID_QUERY, commentId);


    }


    @Override
    public Long save(UserCommentRating entity) throws DaoException {

        Long userId = entity.getUserId();
        Long commentId = entity.getCommentId();
        CommentEstimate estimate = entity.getEstimate();


        try(PreparedStatement statement = createPrepareStatement(INSERT_QUERY,
                userId, commentId, estimate.name())){
            statement.executeUpdate();
            return -1L;//todo stub

        } catch (SQLException e) {
            throw new DaoException("Can't save with entity: " + entity.toString(), e);
        }

    }
}
