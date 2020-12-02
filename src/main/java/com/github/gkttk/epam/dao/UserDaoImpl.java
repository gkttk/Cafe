package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.dao.mappers.RowMapper;
import com.github.gkttk.epam.dao.parsers.EntityParser;
import com.github.gkttk.epam.model.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private final static String TABLE_NAME = "user";
    private final static String SAVE_QUERY = "INSERT INTO user(login, password, role, points, money, active) values(?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_QUERY = "UPDATE user " +
            "SET login = ?, password = ?, role = ?, points = ?, money = ?, active = ? " +
            "WHERE id = ?";
    private final static String FIND_BY_LOGIN_QUERY = "SELECT * FROM user WHERE login = ?";


    public UserDaoImpl(Connection connection, RowMapper<User> rowMapper, EntityParser<User> entityParser) {
        super(connection, rowMapper, entityParser);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    public Optional<User> findByLogin(String login){
        return getSingleResult(FIND_BY_LOGIN_QUERY, login);
    }
}
