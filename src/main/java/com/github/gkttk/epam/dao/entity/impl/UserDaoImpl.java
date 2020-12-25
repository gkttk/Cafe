package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.extractors.UserFieldExtractor;
import com.github.gkttk.epam.dao.mappers.UserRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private final static String UPDATE_USER_STATUS_QUERY = "UPDATE users SET blocked = ? WHERE id = ?"; //+

    private final static String TABLE_NAME = "users";
    private final static String FIND_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT * FROM users WHERE login = ? AND password = ?";

    private final static String FIND_ALL_BY_STATUS = "SELECT * FROM " + TABLE_NAME +
             " WHERE blocked = ?";


    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), new UserFieldExtractor());
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException {
        return getSingleResult(FIND_BY_LOGIN_AND_PASSWORD_QUERY, login, password);
    }//+

    @Override
    public List<User> findAllByStatus(boolean isBlocked) throws DaoException {
        return getAllResults(FIND_ALL_BY_STATUS, isBlocked);
    }

  /*  @Override
    public void updateUserStatusById(long userId, boolean newStatus) throws DaoException { //+
        try (PreparedStatement preparedStatement = createPrepareStatement(UPDATE_USER_STATUS_QUERY, newStatus, userId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't updateUserStatusById(userId, newStatus) with userId: %d, newStatus: %b",
                    userId, newStatus), e);
        }
    }*/

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
