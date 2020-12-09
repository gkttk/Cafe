package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.handler.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Comment;

import java.util.List;

public class DishCommentsCommand implements Command {

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final static String COMMENTS_ATTRIBUTE = "dishComments";
    private final static String DISH_ID_PARAMETER = "dishId";
    private final CommentService commentService;

    public DishCommentsCommand(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAMETER);
        Long dishId = Long.parseLong(dishIdParam);

        List<Comment> comments = commentService.getAllByDishId(dishId);
        requestDataHolder.putSessionAttribute(COMMENTS_ATTRIBUTE, comments);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, COMMENTS_PAGE);
        return new CommandResult(COMMENTS_PAGE, true);

    }
}
