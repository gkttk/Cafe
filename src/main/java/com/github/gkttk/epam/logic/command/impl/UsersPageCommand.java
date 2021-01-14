package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.UserInfo;

import java.util.List;

public class UsersPageCommand implements Command {

    private final UserService userService;

    private final static String USERS_ATTR = "users";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";

    public UsersPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        List<UserInfo> users = userService.getAll();
        requestDataHolder.putSessionAttribute(USERS_ATTR, users);
        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, USERS_PAGE);
        return new CommandResult(USERS_PAGE, true);
    }
}
