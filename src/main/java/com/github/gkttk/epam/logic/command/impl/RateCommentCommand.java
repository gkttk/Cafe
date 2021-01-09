package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.logic.service.impl.CommentServiceImpl;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RateCommentCommand implements Command {

    private final CommentServiceImpl commentService;
    private final UserCommentRatingService userCommentRatingService;

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String ESTIMATE_PARAM = "estimate";
    private final static String DISH_COMMENTS_ATTR = "dishComments";
    private final static String ESTIMATES_ATTR = "estimates";
    private final static String DISH_ID_ATTR = "dishId";


    public RateCommentCommand(CommentServiceImpl commentService, UserCommentRatingService userCommentRatingService) {
        this.commentService = commentService;
        this.userCommentRatingService = userCommentRatingService;
    }


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        String commentIdParam = requestDataHolder.getRequestParameter(COMMENT_ID_PARAM);
        long commentId = Long.parseLong(commentIdParam);

        String estimateParam = requestDataHolder.getRequestParameter(ESTIMATE_PARAM);
        boolean isLiked = Boolean.parseBoolean(estimateParam);

        userCommentRatingService.evaluateComment(userId, commentId, isLiked);

        renewSessionData(requestDataHolder, userId);

        return new CommandResult(COMMENTS_PAGE, true);
    }

    private void renewSessionData(RequestDataHolder requestDataHolder, long userId) throws ServiceException {
        List<CommentInfo> newComments = getNewComments(requestDataHolder);
        requestDataHolder.putSessionAttribute(DISH_COMMENTS_ATTR, newComments);

        Map<Long, Boolean> userEstimates = getUserEstimates(requestDataHolder, userId);
        requestDataHolder.putSessionAttribute(ESTIMATES_ATTR, userEstimates);

    }


    private List<CommentInfo> getNewComments(RequestDataHolder requestDataHolder) throws ServiceException {
        List<CommentInfo> dishComments = (List<CommentInfo>) requestDataHolder.getSessionAttribute(DISH_COMMENTS_ATTR);
        List<CommentInfo> newComments = new ArrayList<>(dishComments.size());

        for (CommentInfo comment : dishComments) {
            long commentId = comment.getId();
            Optional<CommentInfo> commentOpt = commentService.getById(commentId);
            commentOpt.ifPresent(newComments::add);
        }

        return newComments;
    }

    private Map<Long, Boolean> getUserEstimates(RequestDataHolder requestDataHolder, long userId) throws ServiceException {
        long dishId = (long) requestDataHolder.getSessionAttribute(DISH_ID_ATTR);
        List<UserCommentRating> commentEstimates = userCommentRatingService.getAllByUserIdAndDishId(userId, dishId);

        return commentEstimates.stream()
                .collect(Collectors.toMap(UserCommentRating::getCommentId, UserCommentRating::isLiked));


    }
}