package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;

public class RegistrationCommand implements Command { //+

    private final UserService userService;

    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String START_PAGE = "index.jsp";
    private final static String ERROR_MESSAGE_ATTR = "errorMessage";
    private final static String ERROR_MESSAGE = "error.message.registration";

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String login = requestDataHolder.getRequestParameter(LOGIN_PARAM);
        String password = requestDataHolder.getRequestParameter(PASSWORD_PARAM);

        boolean isRegistered = userService.registration(login, password);

        if (isRegistered) {
            return new CommandResult(START_PAGE, true);
        } else {
            requestDataHolder.putRequestAttribute(ERROR_MESSAGE_ATTR, ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }

    }
}
