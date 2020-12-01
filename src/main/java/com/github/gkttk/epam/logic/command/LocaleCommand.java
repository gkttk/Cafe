package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocaleCommand implements Command {



    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();
        session.setAttribute("locale", lang);

        String url = request.getContextPath();


        return new CommandResult(url, true);
    }
}
