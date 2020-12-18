package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean login(String login, String password) throws ServiceException;

    Optional<User> getUserById(Long id) throws ServiceException;

    Optional<User> getUserByLogin(String login) throws ServiceException;//todo

    List<User> getUsers() throws ServiceException;

    void setUserStatus(Long userId, boolean status) throws ServiceException;

    boolean registration(String login, String password) throws ServiceException;

    void changeAvatar(User user, String imageRef) throws ServiceException;

    void removeOldImage(String imagePath);

}
