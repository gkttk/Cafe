package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.handler.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;
import java.util.Optional;

public class LoginCommand implements Command {

    private final static String USER_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String START_PAGE = "index.jsp";
    private final static String ERROR_MESSAGE = "Invalid credentials";//todo

    private final UserService userService;
    private final DishService dishService;


    public LoginCommand(UserService userService, DishService dishService) {
        this.userService = userService;
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String login = requestDataHolder.getRequestParameter("login");
        String password = requestDataHolder.getRequestParameter("password");

        boolean isValid = userService.login(login, password);

        if (isValid) {//todo
            Optional<User> userByLogin = userService.getUserByLogin(login);
            if (userByLogin.isPresent()) {
                User user = userByLogin.get();
                requestDataHolder.putSessionAttribute("authUser", user);
                List<Dish> allDishes = dishService.getAllDishes();
                requestDataHolder.putSessionAttribute("dishes", allDishes);
                requestDataHolder.putSessionAttribute("currentPage", USER_PAGE);
            }

            return new CommandResult(USER_PAGE, true);
        } else {
            requestDataHolder.putRequestAttribute("errorMessage", ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }

    }
}
