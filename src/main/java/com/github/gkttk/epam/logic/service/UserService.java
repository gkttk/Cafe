package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String login, String password) throws ServiceException; //+

    Optional<User> getUserById(long id) throws ServiceException;//+

    List<User> getUsers() throws ServiceException; //+

    Optional<User> changeUserStatus(long userId, boolean status) throws ServiceException;//+




    /*    Optional<User> getUserByLogin(String login) throws ServiceException;//todo*/




    boolean registration(String login, String password) throws ServiceException;

    void changeAvatar(User user, String imageRef) throws ServiceException;

    void removeOldImage(String imagePath);

    List<User> getUsersByStatus(UserStatus userStatus) throws ServiceException;
}

