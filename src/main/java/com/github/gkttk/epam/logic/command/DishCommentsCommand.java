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

    private final CommentService commentService;
    private final UserCommentRatingService userCommentRatingService;

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String DISH_ID_PARAM = "dishId";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String PAGE_NUMBER = "pageNumber";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String PAGE_COUNT_ATTR = "pageCount";
    private final static String ESTIMATES_ATTR = "estimates";


    public DishCommentsCommand(CommentService commentService, UserCommentRatingService userCommentRatingService) {
        this.commentService = commentService;
        this.userCommentRatingService = userCommentRatingService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        long dishId = getDishId(requestDataHolder);

        requestDataHolder.putSessionAttribute(DISH_ID_ATTR, dishId);


        Map<Long, Boolean> userEstimates = getUserEstimates(userId, dishId);

        requestDataHolder.putSessionAttribute(ESTIMATES_ATTR, userEstimates);


        int pageNumber = getPageNumber(requestDataHolder);
        requestDataHolder.putSessionAttribute("currentPagePagination", pageNumber);//todo

        int pageCount = commentService.getPageCount(dishId);
        requestDataHolder.putSessionAttribute(PAGE_COUNT_ATTR, pageCount);//todo request or session?

        List<CommentInfo> comments = commentService.getAllByDishIdPagination(dishId, pageNumber);

        requestDataHolder.putSessionAttribute(COMMENTS_ATTR, comments);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, COMMENTS_PAGE);
        return new CommandResult(COMMENTS_PAGE, true);

    }


    private long getDishId(RequestDataHolder requestDataHolder) {
        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAM);
        return dishIdParam == null ? (long) requestDataHolder.getSessionAttribute(DISH_ID_ATTR) : Long.parseLong(dishIdParam);
    }


    private Map<Long, Boolean> getUserEstimates(long userId, long dishId) throws ServiceException {
        List<UserCommentRating> commentEstimates = userCommentRatingService.getAllByUserIdAndDishId(userId, dishId);

        Map<Long, Boolean> userEstimates = new HashMap<>();

        for (UserCommentRating userCommentRating : commentEstimates) {
            long commentId = userCommentRating.getCommentId();
            boolean isLiked = userCommentRating.isLiked();
            userEstimates.put(commentId, isLiked);
        }

        return userEstimates;
    }


    private int getPageNumber(RequestDataHolder requestDataHolder) {
        String pageNumberParam = requestDataHolder.getRequestParameter(PAGE_NUMBER);
        return pageNumberParam == null ? 1 : Integer.parseInt(pageNumberParam);

    }


}
