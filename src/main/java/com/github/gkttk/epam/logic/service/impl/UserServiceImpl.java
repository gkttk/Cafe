package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.dto.UserInfoDao;
import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.builder.UserBuilder;
import com.github.gkttk.epam.model.builder.UserInfoBuilder;
import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final DaoHelperFactory daoHelperFactory;


    public UserServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelperImpl.createUserDao();
            return userDao.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't login(login, password) with login: %s, password: %s",
                    login, password), e);
        }
    }

    @Override
    public Optional<User> getById(long id) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelperImpl.createUserDao();
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUserById(id) with id: %d", id), e);
        }
    }

    @Override
    public List<UserInfo> getAll() throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserInfoDao userInfoDao = daoHelperImpl.createUserInfoDao();
            return userInfoDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getUsers()", e);
        }
    }


    @Override
    public Optional<UserInfo> changeUserStatus(long userId, boolean newStatus) throws ServiceException {
        Optional<UserInfo> result = Optional.empty();
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserInfoDao userInfoDao = daoHelperImpl.createUserInfoDao();
            Optional<UserInfo> userInfoOpt = userInfoDao.findById(userId);
            if (userInfoOpt.isPresent()) {
                UserInfo userInfoFromDb = userInfoOpt.get();
                if (!compareStatuses(userInfoFromDb, newStatus)) {
                    UserInfoBuilder builder = userInfoFromDb.builder();
                    builder.setBlocked(newStatus);
                    UserInfo userInfo = builder.build();
                    userInfoDao.save(userInfo);
                    result = Optional.of(userInfo);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't setUserStatus(userId, newStatus) with userId %d, newStatus: %b",
                    userId, newStatus), e);
        }
        return result;
    }

    @Override
    public boolean registration(String login, String password) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelperImpl.createUserDao();
            User user = new User(login, password);
            userDao.save(user);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't registration(login, password) with login: %s and password: %s",
                    login, password), e);
        }
    }

    @Override
    public void changeAvatar(User user, String imageRef) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelperImpl.createUserDao();
            UserBuilder userBuilder = user.builder();
            userBuilder.setImageRef(imageRef);
            User newUser = userBuilder.build();
            userDao.save(newUser);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't changeAvatar user with user: %s and imageRef: %s",
                    user.toString(), imageRef), e);
        }
    }

    @Override
    public List<UserInfo> getByStatus(UserStatus userStatus) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserInfoDao userInfoDao = daoHelperImpl.createUserInfoDao();
            boolean isBlocked = userStatus.isBlocked();
            return userInfoDao.findAllByStatus(isBlocked);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getUsersByStatus() with userStatus: %s",
                    userStatus.name()), e);
        }
    }

    @Override
    public void changePoints(long userId, int points, boolean isAdd) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserInfoDao userInfoDao = daoHelperImpl.createUserInfoDao();
            Optional<UserInfo> userOpt = userInfoDao.findById(userId);
            if (userOpt.isPresent()) {
                UserInfo user = userOpt.get();
                int userPoints = user.getPoints();
                int newUserPoints = isAdd ? userPoints + points : userPoints - points;
                UserInfoBuilder userBuilder = user.builder();
                userBuilder.setPoints(newUserPoints);
                UserInfo newUser = userBuilder.build();
                userInfoDao.save(newUser);
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't changePoints(userId, points, isAdd) with userId: %d, points: %d," +
                            " isAdd: %b",
                    userId, points, isAdd), e);
        }
    }

    @Override
    public void addMoney(long userId, BigDecimal money) throws ServiceException {
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelperImpl.createUserDao();
            Optional<User> userOpt = userDao.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                BigDecimal currentMoney = user.getMoney();
                BigDecimal newMoney = currentMoney.add(money);
                UserBuilder builder = user.builder();
                builder.setMoney(newMoney);
                User newUser = builder.build();
                userDao.save(newUser);
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't addMoney(userId,money) with userId: %d, money: %.2f",
                    userId, money), e);
        }
    }


    private boolean compareStatuses(UserInfo userFromDb, boolean newStatus) {
        boolean userFromDbStatus = userFromDb.isBlocked();
        return userFromDbStatus == newStatus;
    }


}
