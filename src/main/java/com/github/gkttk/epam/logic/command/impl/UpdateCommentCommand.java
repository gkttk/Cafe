package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;

import java.util.List;
import java.util.Optional;

public class UpdateCommentCommand implements Command {

    private final CommentService commentService;

    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String COMMENT_TEXT_PARAM = "commentText";
    private final static String DISH_COMMENTS_ATTR = "dishComments";
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";

    public UpdateCommentCommand(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String commentIdParam = requestDataHolder.getRequestParameter(COMMENT_ID_PARAM);
        long commentId = Long.parseLong(commentIdParam);
        String commentText = requestDataHolder.getRequestParameter(COMMENT_TEXT_PARAM);

        commentService.updateComment(commentId, commentText);

        renewSession(requestDataHolder, commentId);

        return new CommandResult(COMMENTS_PAGE, true);
    }

    private void renewSession(RequestDataHolder requestDataHolder, long commentId) throws ServiceException {

        List<CommentInfo> dishComments = (List<CommentInfo>) requestDataHolder.getSessionAttribute(DISH_COMMENTS_ATTR);
        Optional<CommentInfo> commentOpt = dishComments.stream()
                .filter(dishComment -> dishComment
                        .getId().equals(commentId))
                .findFirst();

        if (commentOpt.isPresent()) {
            CommentInfo comment = commentOpt.get();
            int index = dishComments.indexOf(comment);
            boolean isRemoved = dishComments.remove(comment);
            if (isRemoved) {
                Optional<CommentInfo> newCommentOpt = commentService.getById(commentId);
                newCommentOpt.ifPresent(commentInfo -> {
                    dishComments.add(index, commentInfo);
                    requestDataHolder.putSessionAttribute(DISH_COMMENTS_ATTR, dishComments);
                });

            }
        }
    }
}
