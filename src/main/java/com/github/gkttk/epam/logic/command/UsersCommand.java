package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;

public class UsersCommand implements Command {

    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";
    private final static String USERS_ATTRIBUTE_KEY = "users";
    private final UserService userService;
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        List<User> users = userService.getUsers();
        requestDataHolder.putSessionAttribute(USERS_ATTRIBUTE_KEY, users);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, USERS_PAGE);

        return new CommandResult(USERS_PAGE, true);

    }
}
