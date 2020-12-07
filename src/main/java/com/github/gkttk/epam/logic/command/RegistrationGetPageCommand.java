package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationGetPageCommand implements Command {

    private final static String REGISTRATION_PAGE = "/WEB-INF/view/registration_page.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE_PARAMETER, REGISTRATION_PAGE);

        return new CommandResult(REGISTRATION_PAGE, false);

    }
}
