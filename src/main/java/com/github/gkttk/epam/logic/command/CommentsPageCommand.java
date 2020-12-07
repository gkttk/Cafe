package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommentsPageCommand implements Command {

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult(COMMENTS_PAGE, true);

    }
}
