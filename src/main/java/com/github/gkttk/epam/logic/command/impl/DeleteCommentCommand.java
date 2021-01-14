package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.enums.CommentSortTypes;

import java.util.List;

public class DeleteCommentCommand implements Command {

    private final CommentService commentService;
    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String PAGE_COUNT_ATTR = "pageCount";
    private final static String CURRENT_PAGE_PAGINATION_ATTR = "currentPagePagination";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String SORT_TYPE_ATTR = "sortType";

    public DeleteCommentCommand(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String commentIdParam = requestDataHolder.getRequestParameter(COMMENT_ID_PARAM);
        long commentId = Long.parseLong(commentIdParam);
        commentService.removeComment(commentId);
        renewSession(requestDataHolder, commentId);
        return new CommandResult(COMMENTS_PAGE, true);
    }

    private void renewSession(RequestDataHolder requestDataHolder, long commentId) throws ServiceException {

        List<CommentInfo> dishComments = (List<CommentInfo>) requestDataHolder.getSessionAttribute(COMMENTS_ATTR);

        boolean isRemoved = dishComments.removeIf(comment -> comment.getId().equals(commentId));
        if (isRemoved) {
            long dishId = (long) requestDataHolder.getSessionAttribute(DISH_ID_ATTR);

            int newPageCount = commentService.getPageCount(dishId);
            requestDataHolder.putSessionAttribute(PAGE_COUNT_ATTR, newPageCount);

            int currentPageNumber = (int) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PAGINATION_ATTR);
            CommentSortTypes sortType = (CommentSortTypes) requestDataHolder.getSessionAttribute(SORT_TYPE_ATTR);
            List<CommentInfo> comments = commentService.getAllByDishIdPagination(dishId, currentPageNumber, sortType);
            requestDataHolder.putSessionAttribute(COMMENTS_ATTR, comments);
        }
    }
}
