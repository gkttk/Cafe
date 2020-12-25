package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.dto.UserInfoDao;
import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.logic.validator.factory.ValidatorFactory;
import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserStatus;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't login(login, password) with login: %s, password: %s",
                    login, password), e);
        }
    }//+

    @Override
    public Optional<User> getUserById(long id) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserById(id) with id: %d", id), e);
        }
    }//+

    @Override
    public List<UserInfo> getUsers() throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserInfoDao userInfoDao = daoHelper.createUserInfoDao();
            return userInfoDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getUsers()", e);
        }
    } //+


    @Override
    public Optional<UserInfo> changeUserStatus(long userId, boolean newStatus) throws ServiceException { //+
        Optional<UserInfo> result = Optional.empty();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserInfoDao userInfoDao = daoHelper.createUserInfoDao();
            Optional<UserInfo> userInfoOpt = userInfoDao.findById(userId);
            if (userInfoOpt.isPresent()) {
                UserInfo userInfoFromDb = userInfoOpt.get();
                if (!compareStatuses(userInfoFromDb, newStatus)) {
                    UserInfo userInfo  = userInfoFromDb.changeActive(newStatus); //todo need entity which can change fields of entities
                    userInfoDao.save(userInfo);
                    result = Optional.of(userInfo);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't setUserStatus(userId, newStatus) with userId %d, newStatus: %b",
                    userId, newStatus), e);
        }

        return result;
    }//+

    @Override
    public boolean registration(String login, String password) throws ServiceException {
        Validator userLoginValidator = ValidatorFactory.getUserLoginValidator();
        Validator userPasswordValidator = ValidatorFactory.getUserPasswordValidator();

        boolean isLoginValid = userLoginValidator.validate(login);
        boolean isPasswordValid = userPasswordValidator.validate(password);

        if (!isLoginValid || !isPasswordValid) {
            return false;
        }

        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            User user = new User(login, password);
            userDao.save(user);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't registration(login, password) with login: %s and password: %s",
                    login, password), e);
        }
    } //+



  /*  @Override
    public Optional<User> getUserByLogin(String login) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findByLoginAndPassword(login);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserByLogin() with login: %s", login), e);
        }

    }*/


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
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public List<UserInfo> getUsersByStatus(UserStatus userStatus) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            UserInfoDao userInfoDao = daoHelper.createUserInfoDao();
            boolean isBlocked = userStatus.isBlocked();
            return userInfoDao.findAllByStatus(isBlocked);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUsersByStatus() with userStatus: %s",
                    userStatus.name()), e);
        }

    }


    private boolean compareStatuses(UserInfo userFromDb, boolean newStatus) {
        boolean userFromDbStatus = userFromDb.isBlocked();
        return userFromDbStatus == newStatus;
    } //+


}
