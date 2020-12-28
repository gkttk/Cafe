package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.model.CommandResult;

public class RegistrationGetPageCommand implements Command {

    private final static String REGISTRATION_PAGE = "/WEB-INF/view/registration_page.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, REGISTRATION_PAGE);

        return new CommandResult(REGISTRATION_PAGE, false);

    }
}
