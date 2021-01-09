package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;

public class LogoutCommand implements Command {

    private final static String START_PAGE = "index.jsp";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {
        requestDataHolder.invalidateSession();
        return new CommandResult(START_PAGE, true);

    }
}
