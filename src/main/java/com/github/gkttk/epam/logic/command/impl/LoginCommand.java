package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;
import java.util.Optional;

public class LoginCommand implements Command {

    private final UserService userService;
    private final DishService dishService;

    private final static String USER_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String START_PAGE = "index.jsp";
    private final static String ERROR_MESSAGE_CREDENTIALS = "error.message.credentials";
    private final static String ERROR_MESSAGE_BLOCKED = "error.message.blocked";
    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String DISHES_ATTR = "dishes";
    private final static String CURRENT_PAGE_ATTR = "currentPage";
    private final static String MESSAGE_ATTR = "message";

    public LoginCommand(UserService userService, DishService dishService) {
        this.userService = userService;
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String login = requestDataHolder.getRequestParameter(LOGIN_PARAM);
        String password = requestDataHolder.getRequestParameter(PASSWORD_PARAM);

        Optional<User> userOpt = userService.login(login, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.isBlocked()) {
                requestDataHolder.putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_BLOCKED);
                return new CommandResult(START_PAGE, false);
            }

            requestDataHolder.putSessionAttribute(AUTH_USER_ATTR, user);
            List<Dish> allDishes = dishService.getAllEnabled();

            requestDataHolder.putSessionAttribute(DISHES_ATTR, allDishes);
            requestDataHolder.putSessionAttribute(CURRENT_PAGE_ATTR, USER_PAGE);

            return new CommandResult(USER_PAGE, true);
        } else {
            requestDataHolder.putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_CREDENTIALS);
            return new CommandResult(START_PAGE, false);
        }


    }
}
