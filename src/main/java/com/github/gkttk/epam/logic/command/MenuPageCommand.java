package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MenuPageCommand implements Command {

    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult(MENU_PAGE, true);

    }
}
