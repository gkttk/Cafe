package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;

public class LocaleCommand implements Command {

    private final static String START_PAGE = "index.jsp";
    private final static String LANGUAGE_PARAM = "lang";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String LOCALE_ATTR = "locale";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {
        String lang = requestDataHolder.getRequestParameter(LANGUAGE_PARAM);

        requestDataHolder.putSessionAttribute(LOCALE_ATTR, lang);

        String redirectPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAM);
        if (redirectPage == null) {
            redirectPage = START_PAGE;
        }
        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, redirectPage);

        return new CommandResult(redirectPage, false);
    }
}
