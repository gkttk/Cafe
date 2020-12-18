package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;

public class RegistrationCommand implements Command {

    private final static String START_PAGE = "index.jsp";

    private final UserService userService;
    private final static String ERROR_MESSAGE = "Can't register";//todo


    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String login = requestDataHolder.getRequestParameter("login");
        String password = requestDataHolder.getRequestParameter("password");

        boolean isRegistered = userService.registration(login, password);//todo boolean

        if(isRegistered){
            return new CommandResult(START_PAGE, true);
        }else {
            requestDataHolder.putRequestAttribute("errorMessage", ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }


    }
}
