package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements Command {

    private final static String START_PAGE = "index.jsp";

    private final UserService userService;
    private final static String ERROR_MESSAGE = "Can't register";//todo


    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isRegistered = userService.registration(login, password);//todo boolean

        if(isRegistered){
            return new CommandResult(START_PAGE, true);
        }else {
            request.setAttribute("errorMessage", ERROR_MESSAGE);
            return new CommandResult(START_PAGE, false);
        }


    }
}
