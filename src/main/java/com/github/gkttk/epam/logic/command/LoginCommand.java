package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isValid = userService.login(login, password);

        if (isValid) {//todo
            Optional<User> userByLogin = userService.getUserByLogin(login);
            if (userByLogin.isPresent()) {
                User user = userByLogin.get();
                HttpSession session = request.getSession();
                session.setAttribute("authUser", user);
                List<Dish> allDishes = dishService.getAllDishes();
                session.setAttribute("dishes", allDishes);
            }

            return new CommandResult(USER_PAGE, false);
        } else {
            request.setAttribute("errorMessage", ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }

    }
}
