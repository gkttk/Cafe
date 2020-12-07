package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MenuPageCommand implements Command {

    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE_PARAMETER, MENU_PAGE);
        return new CommandResult(MENU_PAGE, true);

    }
}
