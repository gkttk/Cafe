package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.enums.CurrentPages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocaleCommand implements Command {

    private final static String START_PAGE = "index.jsp";
    private final static String LANGUAGE_PARAMETER = "lang";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";


    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        String lang = request.getParameter(LANGUAGE_PARAMETER);

        HttpSession session = request.getSession();
        session.setAttribute("locale", lang);

        String refForRedirect = (String) session.getAttribute(CURRENT_PAGE_PARAMETER);
        if(refForRedirect == null){
            refForRedirect = START_PAGE;
            session.setAttribute(CURRENT_PAGE_PARAMETER, START_PAGE);
        }

        return new CommandResult(refForRedirect, true);
    }
}
