package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HomeCommand implements Command {

    private final static String HOME_PAGE = "index.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE_PARAMETER, HOME_PAGE);
        return new CommandResult(HOME_PAGE, false);

    }
}
