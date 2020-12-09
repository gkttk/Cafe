package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.handler.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.CommandResult;

public class LogoutCommand implements Command {

    private final static String START_PAGE = "index.jsp";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        requestDataHolder.invalidateSession();
        return new CommandResult(START_PAGE, true);

    }
}
