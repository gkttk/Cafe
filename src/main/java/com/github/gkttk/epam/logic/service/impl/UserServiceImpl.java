package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.entities.User;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {


    @Override
    public boolean login(String login, String password) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> userOptional = userDao.findByLogin(login);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean active = user.isActive();
                boolean isPasswordValid = checkPassword(userOptional.get(), password);
                return active && isPasswordValid;
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't login() with login: %s, password: %s",
                    login, password), e);
        }
    }

    @Override
    public Optional<User> getUserById(Long id) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserById() with id: %d", id), e);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserByLogin() with login: %s", login), e);
        }

    }

    @Override
    public List<User> getUsers() throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getUsers().", e);
        }
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
            userDao.save(user);//todo
            return true;
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't registration user with login: %s and password: %s",
                    login, password), e);

        }
    }

    @Override
    public void changeAvatar(User user, String imageRef) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            User newUser = user.changeImageRef(imageRef);
            userDao.save(newUser);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't changeAvatar user with user: %s and imageRef: %s",
                    user.toString(), imageRef), e);

        }
    }

    @Override
    public void removeOldImage(String imagePath) {
        File file = new File(imagePath);
        if(file.exists()){
            file.delete();
        }
    }

    private boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

}
