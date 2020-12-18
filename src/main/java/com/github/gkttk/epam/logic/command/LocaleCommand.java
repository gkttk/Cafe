package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.model.CommandResult;

public class LocaleCommand implements Command {

    private final static String START_PAGE = "index.jsp";
    private final static String LANGUAGE_PARAMETER = "lang";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {

        String lang = requestDataHolder.getRequestParameter(LANGUAGE_PARAMETER);

        requestDataHolder.putSessionAttribute("locale", lang);

        String refForRedirect = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAMETER);
        if(refForRedirect == null){
            refForRedirect = START_PAGE;
            requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, START_PAGE);
        }

        return new CommandResult(refForRedirect, true);
    }
}
