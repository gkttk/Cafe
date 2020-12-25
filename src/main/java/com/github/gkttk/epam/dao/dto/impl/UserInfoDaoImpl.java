package com.github.gkttk.epam.dao.dto.impl;

import com.github.gkttk.epam.dao.dto.UserInfoDao;
import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.extractors.UserInfoFieldExtractor;
import com.github.gkttk.epam.dao.mappers.UserInfoRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.dto.UserInfo;

import java.sql.Connection;
import java.util.List;

public class UserInfoDaoImpl extends AbstractDao<UserInfo> implements UserInfoDao {

    private final static String TABLE_NAME = "users";
    private final static String FIND_ALL_BY_STATUS = "SELECT id, login, role, points, blocked FROM "
            + TABLE_NAME + " WHERE blocked = ?";


    public UserInfoDaoImpl(Connection connection) {
        super(connection, new UserInfoRowMapper(), new UserInfoFieldExtractor());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    @Override
    public List<UserInfo> findAllByStatus(boolean isBlocked) throws DaoException {
        return getAllResults(FIND_ALL_BY_STATUS, isBlocked);
    }
}
