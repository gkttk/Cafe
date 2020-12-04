package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.User;

import java.util.Optional;

public interface UserService {
    boolean login(String login, String password) throws ServiceException;

    Optional<User> getUserByLogin(String login) throws ServiceException;//todo

}
