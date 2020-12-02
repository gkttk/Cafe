package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.model.entities.User;

import java.util.Optional;

public interface UserService {
    boolean login(String login, String password);

    Optional<User> getUserByLogin(String login);//todo

}
