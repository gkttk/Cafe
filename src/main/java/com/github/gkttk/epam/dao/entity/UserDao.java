package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.User;

import java.util.Optional;


/**
 * Dao for working with User entity.
 */
public interface UserDao extends Dao<User> {

    Optional<User> findByLoginAndPassword(String login, String password) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    void updatePassword(String password, long userId) throws DaoException;

}
