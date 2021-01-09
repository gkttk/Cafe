package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserStatus;

import java.util.List;

public class SortUsersCommand implements Command {

    private final UserService userService;

    private final static String USER_STATUS_PARAM = "userStatus";
    private final static String USERS_ATTR = "users";
    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";


    public SortUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        String userStatusParam = requestDataHolder.getRequestParameter(USER_STATUS_PARAM);

        List<UserInfo> users;
        if (userStatusParam != null) {
            UserStatus userStatus = UserStatus.valueOf(userStatusParam);
            users = userService.getByStatus(userStatus);
        } else {
            users = userService.getAll();
        }

        requestDataHolder.putSessionAttribute(USERS_ATTR, users);

        return new CommandResult(USERS_PAGE, true);
    }
}
