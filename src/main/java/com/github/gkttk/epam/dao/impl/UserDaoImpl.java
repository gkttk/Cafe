package com.github.gkttk.epam.dao.impl;

import com.github.gkttk.epam.dao.AbstractDao;
import com.github.gkttk.epam.dao.UserDao;
import com.github.gkttk.epam.dao.extractors.UserFieldExtractor;
import com.github.gkttk.epam.dao.mappers.UserRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.User;

import java.sql.Connection;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private final static String TABLE_NAME = "user";
    private final static String FIND_BY_LOGIN_QUERY = "SELECT * FROM user WHERE login = ?";


    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), new UserFieldExtractor());
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        return getSingleResult(FIND_BY_LOGIN_QUERY, login);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
