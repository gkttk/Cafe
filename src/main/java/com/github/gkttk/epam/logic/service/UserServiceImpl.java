package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.dao.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {


    private final DaoHelperFactory factory;

    public UserServiceImpl(DaoHelperFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean login(String login, String password) throws ServiceException {
        boolean isValid = false;
        try(DaoHelper daoHelper = factory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> userOptional = userDao.findByLogin(login);
            if (userOptional.isPresent()) {
                isValid = checkPassword(userOptional.get(), password);
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't login() with login: %s, password: %s",
                    login, password), e);
        }
        return isValid;
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws ServiceException {
        Optional<User> user = Optional.empty();
        try(DaoHelper daoHelper = factory.createDaoHelper())  {
            UserDao userDao = daoHelper.createUserDao();
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserByLogin() with login: %s", login), e);
        }
        return user;
    }


    private boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

}
