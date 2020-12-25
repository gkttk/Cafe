package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Comment;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo mb this class is no used
public class CommentsPageCommand implements Command {

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final static String COMMENTS_ATTRIBUTE = "dishComments";
    private final CommentService commentService;
    private final UserCommentRatingService userCommentRatingService;

    public CommentsPageCommand(CommentService commentService, UserCommentRatingService userCommentRatingService) {
        this.commentService = commentService;
        this.userCommentRatingService = userCommentRatingService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User)requestDataHolder.getSessionAttribute("authUser");
        Long userId = authUser.getId();

        //todo add getAllByUserIdAndDishId
        List<UserCommentRating> commentEstimates = userCommentRatingService.getAllByUserId(userId);

        Map<Long, Boolean> userEstimates = new HashMap<>();

        for (UserCommentRating userCommentRating: commentEstimates){
            Long commentId = userCommentRating.getCommentId();
            Boolean isLiked = userCommentRating.isLiked();
            userEstimates.put(commentId, isLiked);
        }

        requestDataHolder.putSessionAttribute("estimates", userEstimates);


        List<Comment> comments = commentService.getAll();
        comments.sort((firstComm, secondComm)-> (int)(firstComm.getId() - secondComm.getId()));


        requestDataHolder.putSessionAttribute(COMMENTS_ATTRIBUTE, comments);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, COMMENTS_PAGE);
        return new CommandResult(COMMENTS_PAGE, true);

    }
}
