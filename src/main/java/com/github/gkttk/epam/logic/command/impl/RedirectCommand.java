package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;

public class RedirectCommand implements Command {
    private final static String CURRENT_PAGE_ATTR = "currentPage";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String redirectPage = (String)requestDataHolder.getSessionAttribute(CURRENT_PAGE_ATTR);
        return new CommandResult(redirectPage, false);
    }
}
