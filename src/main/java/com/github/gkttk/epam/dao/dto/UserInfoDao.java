package com.github.gkttk.epam.dao.dto;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.dto.UserInfo;

import java.util.List;

public interface UserInfoDao extends Dao<UserInfo> {

    List<UserInfo> findAllByStatus(boolean isBlocked) throws DaoException;

}
