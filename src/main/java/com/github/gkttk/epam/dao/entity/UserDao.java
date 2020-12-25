package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> findByLoginAndPassword(String login, String password) throws DaoException;//+

    List<User> findAllByStatus(boolean isBlocked) throws DaoException;

    /* void updateUserStatusById(long id, boolean newStatus) throws DaoException; //+*/

}
