package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.logic.service.impl.CommentServiceImpl;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RateCommentCommand implements Command {

    private final CommentServiceImpl commentService;
    private final UserCommentRatingService userCommentRatingService;

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String COMMENT_ID_PARAM = "commentId";
    private final static String ESTIMATE_PARAM = "estimate";
    private final static String RATING_PARAM = "rating";
    private final static String DISH_COMMENTS_ATTR = "dishComments";


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

        String commentRatingParam = requestDataHolder.getRequestParameter(RATING_PARAM);
        int commentRating = Integer.parseInt(commentRatingParam);


        userCommentRatingService.evaluateComment(userId, commentId, commentRating, isLiked);

        renewSessionData(requestDataHolder, userId, commentId);

        return new CommandResult(COMMENTS_PAGE, true);
    }


    private void renewSessionData(RequestDataHolder requestDataHolder, long userId, long commentId) throws ServiceException {
        List<CommentInfo> dishComments = (List<CommentInfo>) requestDataHolder.getSessionAttribute(DISH_COMMENTS_ATTR);

        Optional<CommentInfo> commentOpt = commentService.getById(commentId);
        if (commentOpt.isPresent()) {
            dishComments.removeIf(commentInfo -> commentInfo.getId().equals(commentId));
            CommentInfo comment = commentOpt.get();
            dishComments.add(comment);
        }
        dishComments.sort((firstComm, secondComm) -> (int) (firstComm.getId() - secondComm.getId()));//todo sort???

        requestDataHolder.putSessionAttribute("dishComments", dishComments);


        List<UserCommentRating> commentEstimates = userCommentRatingService.getAllByUserId(userId);

        Map<Long, Boolean> userEstimates = new HashMap<>();

        for (UserCommentRating userCommentRating : commentEstimates) {
            Long commentId1 = userCommentRating.getCommentId();
            Boolean isLiked = userCommentRating.isLiked();
            userEstimates.put(commentId1, isLiked);
        }

        requestDataHolder.putSessionAttribute("estimates", userEstimates);


    }
}