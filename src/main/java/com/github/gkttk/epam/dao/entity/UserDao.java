package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> findByLogin(String login) throws DaoException;
    void updateUserActiveById(Long id, boolean status) throws DaoException;

}
