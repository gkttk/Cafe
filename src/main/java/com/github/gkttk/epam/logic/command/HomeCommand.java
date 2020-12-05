package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeCommand implements Command {

    private final static String HOME_PAGE = "index.jsp";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return new CommandResult(HOME_PAGE, false);

    }
}
