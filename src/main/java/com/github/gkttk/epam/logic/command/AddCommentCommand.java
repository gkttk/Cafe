package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;

public class AddCommentCommand implements Command {


    private final CommentService commentService;
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String COMMENT_TEXT_PARAM = "commentText";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String DISH_ID_ATTR = "dishId";


    public AddCommentCommand(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        String commentText = requestDataHolder.getRequestParameter(COMMENT_TEXT_PARAM);
        long dishId = (Long) requestDataHolder.getSessionAttribute(DISH_ID_ATTR);

        commentService.addComment(commentText, userId, dishId);

        renewSession(requestDataHolder, dishId);

        return new CommandResult(COMMENTS_PAGE, true);
    }

    private void renewSession(RequestDataHolder requestDataHolder, long dishId) throws ServiceException {
        List<CommentInfo> comments = commentService.getAllByDishId(dishId);
        requestDataHolder.putSessionAttribute(COMMENTS_ATTR, comments);
    }

}
