package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.model.CommandResult;

public class MenuPageCommand implements Command {

    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {
        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, MENU_PAGE);
        return new CommandResult(MENU_PAGE, true);

    }
}
