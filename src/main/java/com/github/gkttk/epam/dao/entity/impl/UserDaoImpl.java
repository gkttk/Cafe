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
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private final static String TABLE_NAME = "users";
    private final static String FIND_BY_LOGIN_QUERY = "SELECT * FROM users WHERE login = ?";
    private final static String SET_ACTIVE_USER = "UPDATE users SET active = ? WHERE id = ?";


    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), new UserFieldExtractor());
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        return getSingleResult(FIND_BY_LOGIN_QUERY, login);
    }

    @Override
    public void updateUserActiveById(Long id, boolean status) throws DaoException {
        try(PreparedStatement preparedStatement = createPrepareStatement(SET_ACTIVE_USER, status, id)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't set status: %b for user with id: %d.", status, id), e);
        }


    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
