package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UsersCommand implements Command {

    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";
    private final static String USERS_ATTRIBUTE_KEY = "users";
    private final UserService userService;


    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        List<User> users = userService.getUsers();
        HttpSession session = request.getSession();
        session.setAttribute(USERS_ATTRIBUTE_KEY, users);

        return new CommandResult(USERS_PAGE, true);

    }
}
