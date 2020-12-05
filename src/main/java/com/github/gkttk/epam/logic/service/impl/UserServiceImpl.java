package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {


    @Override
    public boolean login(String login, String password) throws ServiceException {
        boolean isValid = false;
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
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
    public Optional<User> getUserById(Long id) throws ServiceException {
        Optional<User> result = Optional.empty();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            result = userDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserById() with id: %d", id), e);
        }
        return result;
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws ServiceException {
        Optional<User> user = Optional.empty();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserByLogin() with login: %s", login), e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() throws ServiceException {
        List<User> users = new ArrayList<>();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getUsers().", e);
        }
        return users;
    }

    @Override
    public void setUserStatus(Long userId, boolean status) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            userDao.updateUserActiveById(userId, status);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't set status %b for user with id %d", status, userId), e);
        }
    }

    @Override
    public boolean registration(String login, String password) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            User user = new User(login, password);
            return userDao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't registration user with login: %s and password: %s",
                    login, password), e);

        }
    }


    private boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

}
