package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishCommentsCommand implements Command {

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final static String COMMENTS_ATTRIBUTE = "dishComments";
    private final static String DISH_ID_PARAMETER = "dishId";
    private final CommentService commentService;
    private final UserCommentRatingService userCommentRatingService;

    public DishCommentsCommand(CommentService commentService, UserCommentRatingService userCommentRatingService) {
        this.commentService = commentService;
        this.userCommentRatingService = userCommentRatingService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute("authUser");
        Long userId = authUser.getId();

        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAMETER);
        long dishId;
        if(dishIdParam == null){
            dishId = (Long) requestDataHolder.getSessionAttribute("dishId");
        }else {
             dishId = Long.parseLong(dishIdParam);
            requestDataHolder.putSessionAttribute("dishId", dishId);
        }//todo



        List<UserCommentRating> commentEstimates = userCommentRatingService.getAllByUserIdAndDishId(userId, dishId);

        Map<Long, Boolean> userEstimates = new HashMap<>();

        for (UserCommentRating userCommentRating : commentEstimates) {
            Long commentId = userCommentRating.getCommentId();
            Boolean isLiked = userCommentRating.isLiked();
            userEstimates.put(commentId, isLiked);
        }

        requestDataHolder.putSessionAttribute("estimates", userEstimates);


        String currentPageParam = requestDataHolder.getRequestParameter("currentPage");
        int currentPage;
        if (currentPageParam != null) {
            currentPage = Integer.parseInt(currentPageParam);
        } else {
            currentPage = 1;
        }



        int pageCount = commentService.getPageCount(dishId);
        requestDataHolder.putSessionAttribute("pageCount", pageCount);

        List<CommentInfo> comments = commentService.getAllByDishIdPagination(dishId, currentPage);


        /* List<CommentInfo> comments = commentService.getAllByDishId(dishId);*/
        requestDataHolder.putSessionAttribute(COMMENTS_ATTRIBUTE, comments);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, COMMENTS_PAGE);
        return new CommandResult(COMMENTS_PAGE, true);

    }
}
