package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;

import java.util.List;

public class DeleteCommentCommand implements Command{

    private final CommentService commentService;
    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String DISH_COMMENTS_ATTR = "dishComments";
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
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


    private void renewSession(RequestDataHolder requestDataHolder, long commentId) {

        List<CommentInfo> dishComments = (List<CommentInfo>) requestDataHolder.getSessionAttribute("dishComments");

        boolean isRemoved = dishComments.removeIf(comment -> comment.getId().equals(commentId));
        if (isRemoved) {
            requestDataHolder.putSessionAttribute(DISH_COMMENTS_ATTR, dishComments);
        }
    }
}
