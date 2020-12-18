package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.logic.service.impl.CommentServiceImpl;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentEstimate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RateCommentCommand implements Command {

    private final CommentServiceImpl commentService;
    private final UserCommentRatingService userCommentRatingService;
    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";


    public RateCommentCommand(CommentServiceImpl commentService, UserCommentRatingService userCommentRatingService) {
        this.commentService = commentService;
        this.userCommentRatingService = userCommentRatingService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {


        User authUser = (User) requestDataHolder.getSessionAttribute("authUser");
        Long userId = authUser.getId();

        String commentIdStr = requestDataHolder.getRequestParameter("commentId");
        long commentId = Long.parseLong(commentIdStr);

        String estimateStr = requestDataHolder.getRequestParameter("estimate");
        CommentEstimate estimate = CommentEstimate.valueOf(estimateStr);


        String commentRatingStr = requestDataHolder.getRequestParameter("rating");
        int commentRating = Integer.parseInt(commentRatingStr);


        int newRating = estimate.changeRating(commentRating);


        boolean isEvaluated = userCommentRatingService.checkCommentWasEvaluated(userId, commentId);
        if (isEvaluated) {
            userCommentRatingService.remove(userId, commentId);

        } else {
            userCommentRatingService.evaluateComment(userId, commentId, estimate);
        }

        commentService.changeCommentRating(newRating, commentId);

        renewSessionData(userId, commentId, requestDataHolder);


        return new CommandResult(COMMENTS_PAGE, true);
    }


    private void renewSessionData(Long userId, Long commentId, RequestDataHolder requestDataHolder) throws ServiceException {
        List<CommentInfo> dishComments = (List<CommentInfo>) requestDataHolder.getSessionAttribute("dishComments");

        Optional<CommentInfo> commentOpt = commentService.getById(commentId);
        if (commentOpt.isPresent()) {
            dishComments.removeIf(commentInfo -> commentInfo.getId().equals(commentId));
            CommentInfo comment = commentOpt.get();
            dishComments.add(comment);
        }
        dishComments.sort((firstComm, secondComm) -> (int) (firstComm.getId() - secondComm.getId()));

        requestDataHolder.putSessionAttribute("dishComments", dishComments);


        List<UserCommentRating> commentEstimates = userCommentRatingService.getAllByUserId(userId);

        Map<Long, CommentEstimate> userEstimates = new HashMap<>();

        for (UserCommentRating userCommentRating : commentEstimates) {
            Long commentId1 = userCommentRating.getCommentId();
            CommentEstimate estimate = userCommentRating.getEstimate();
            userEstimates.put(commentId1, estimate);
        }

        requestDataHolder.putSessionAttribute("estimates", userEstimates);


    }
}