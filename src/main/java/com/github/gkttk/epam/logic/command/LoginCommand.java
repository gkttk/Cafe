package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class LoginCommand implements Command {

    private final static String USER_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String START_PAGE = "index.jsp";
    private final static String ERROR_MESSAGE = "Invalid credentials";//todo

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isValid = userService.login(login, password);

        if (isValid) {
            User user = new User(1L, login, password, UserRole.ADMIN, 50, new BigDecimal(100), true);//todo
            HttpSession session = request.getSession();
            session.setAttribute("authUser", user);
            return new CommandResult(USER_PAGE, false);
        } else {
            request.setAttribute("errorMessage", ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }

    }
}