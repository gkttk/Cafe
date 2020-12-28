package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.model.CommandResult;

public class HomeCommand implements Command {

    private final static String HOME_PAGE = "index.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, HOME_PAGE);
        return new CommandResult(HOME_PAGE, false);

    }
}
