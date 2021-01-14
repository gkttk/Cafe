package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;

public class RegistrationCommand implements Command {

    private final UserService userService;
    private final Validator userLoginValidator;
    private final Validator userPasswordValidator;
    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String START_PAGE = "index.jsp";
    private final static String ERROR_MESSAGE_ATTR = "errorMessage";
    private final static String ERROR_MESSAGE = "error.message.registration";

    public RegistrationCommand(UserService userService, Validator userLoginValidator, Validator userPasswordValidator) {
        this.userService = userService;
        this.userLoginValidator = userLoginValidator;
        this.userPasswordValidator = userPasswordValidator;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String login = requestDataHolder.getRequestParameter(LOGIN_PARAM);
        boolean isLoginValid = userLoginValidator.validate(login);

        String password = requestDataHolder.getRequestParameter(PASSWORD_PARAM);
        boolean isPasswordValid = userPasswordValidator.validate(password);

        if (!isLoginValid || !isPasswordValid) {
            requestDataHolder.putRequestAttribute(ERROR_MESSAGE_ATTR, ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }

        boolean isRegistered = userService.registration(login, password);

        if (isRegistered) {
            return new CommandResult(START_PAGE, true);
        } else {
            requestDataHolder.putRequestAttribute(ERROR_MESSAGE_ATTR, ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }
    }
}
