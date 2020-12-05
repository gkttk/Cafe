package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationGetPageCommand implements Command {

    private final static String REGISTRATION_PAGE = "/WEB-INF/view/registration_page.jsp";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        return new CommandResult(REGISTRATION_PAGE, false);

    }
}
