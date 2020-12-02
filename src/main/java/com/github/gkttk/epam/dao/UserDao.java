package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.model.entities.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> findByLogin(String login) throws SQLException;

}
