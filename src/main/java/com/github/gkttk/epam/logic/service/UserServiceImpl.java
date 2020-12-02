package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.dao.UserDao;
import com.github.gkttk.epam.model.entities.User;

import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final Dao<User> userDao;

    public UserServiceImpl(Dao<User> userDao) {
        this.userDao = userDao;
    }




    @Override
    public boolean login(String login, String password) {
        boolean isValid = false;
        try {
            Optional<User> userOptional = ((UserDao) userDao).findByLogin(login);
            if (userOptional.isPresent()) {
                isValid = checkPassword(userOptional.get(), password);
            }
        } catch (SQLException e) {
            e.printStackTrace();//todo log
        }
        return isValid;
    }

    @Override
    public Optional<User> getUserByLogin(String login) { //todo
        Optional<User> user = Optional.empty();
        try {
            user = ((UserDao) userDao).findByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    private boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

}
