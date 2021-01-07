package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String login, String password) throws ServiceException; //+

    Optional<User> getById(long id) throws ServiceException;//+

    List<UserInfo> getAll() throws ServiceException; //+

    Optional<UserInfo> changeUserStatus(long userId, boolean status) throws ServiceException;//+




    boolean registration(String login, String password) throws ServiceException;

    void changeAvatar(User user, String imageRef) throws ServiceException;


    List<UserInfo> getByStatus(UserStatus userStatus) throws ServiceException;

    void changePoints(long userId, int userPoints, boolean isAdd) throws ServiceException;

    void addMoney(long userId, BigDecimal money) throws ServiceException;
}

