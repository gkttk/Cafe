package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.CommentSortTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AddCommentCommand implements Command {

    private final static Logger LOGGER = LogManager.getLogger(AddCommentCommand.class);
    private final CommentService commentService;
    private final Validator commentValidator;
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String COMMENT_TEXT_PARAM = "commentText";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String PAGE_COUNT_ATTR = "pageCount";
    private final static String CURRENT_PAGE_PAGINATION_ATTR = "currentPagePagination";
    private final static String SORT_TYPE_ATTR = "sortType";
    private final static String ERROR_MESSAGE_KEY = "errorMessage";
    private final static String ERROR_MESSAGE_VALUE = "error.message.wrong.comment";

    private final static int START_PAGE_PAGINATION = 1;

    public AddCommentCommand(CommentService commentService, Validator commentValidator) {
        this.commentService = commentService;
        this.commentValidator = commentValidator;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        String commentText = requestDataHolder.getRequestParameter(COMMENT_TEXT_PARAM);
        boolean isCommentValid = commentValidator.validate(commentText);
        if (!isCommentValid) {
            LOGGER.info("Incorrect comment length: {}", commentText.length());
            requestDataHolder.putRequestAttribute(ERROR_MESSAGE_KEY, ERROR_MESSAGE_VALUE);
            return new CommandResult(COMMENTS_PAGE, false);
        }

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        long dishId = (long) requestDataHolder.getSessionAttribute(DISH_ID_ATTR);

        commentService.addComment(commentText, userId, dishId);

        renewSession(requestDataHolder, dishId);

        return new CommandResult(COMMENTS_PAGE, true);
    }

    private void renewSession(RequestDataHolder requestDataHolder, long dishId) throws ServiceException {
        int newPageCount = commentService.getPageCount(dishId);
        requestDataHolder.putSessionAttribute(PAGE_COUNT_ATTR, newPageCount);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PAGINATION_ATTR, START_PAGE_PAGINATION);
        CommentSortTypes sortType = (CommentSortTypes) requestDataHolder.getSessionAttribute(SORT_TYPE_ATTR);
        List<CommentInfo> comments = commentService.getAllByDishIdPagination(dishId, START_PAGE_PAGINATION, sortType);
        requestDataHolder.putSessionAttribute(COMMENTS_ATTR, comments);


    }

}
