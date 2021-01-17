package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;
import com.github.gkttk.epam.model.enums.CommentSortType;

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
    private final static String SORT_TYPE_ATTR = "sortType";
    private final static String CURRENT_PAGE_PAGINATION_ATTR = "currentPagePagination";
    private final static CommentSortType DEFAULT_SORT_TYPE = CommentSortType.DATE;
    private final static int START_PAGE_NUMBER = 1;

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

        if (!requestDataHolder.isSessionContainKey(ESTIMATES_ATTR)) {
            Map<Long, Boolean> userEstimates = getUserEstimates(userId, dishId);
            requestDataHolder.putSessionAttribute(ESTIMATES_ATTR, userEstimates);
        }

        int pageNumber = getPageNumber(requestDataHolder);
        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PAGINATION_ATTR, pageNumber);

        int pageCount = commentService.getPageCount(dishId);
        requestDataHolder.putSessionAttribute(PAGE_COUNT_ATTR, pageCount);

        CommentSortType sortType = getSortType(requestDataHolder);

        List<CommentInfo> comments = commentService.getAllByDishIdPagination(dishId, pageNumber, sortType);

        requestDataHolder.putSessionAttribute(COMMENTS_ATTR, comments);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, COMMENTS_PAGE);
        return new CommandResult(COMMENTS_PAGE, true);

    }

    private CommentSortType getSortType(RequestDataHolder requestDataHolder) {
        CommentSortType sortType = DEFAULT_SORT_TYPE;
        if (requestDataHolder.isSessionContainKey(SORT_TYPE_ATTR)) {
            sortType = (CommentSortType) requestDataHolder.getSessionAttribute(SORT_TYPE_ATTR);
        } else {
            requestDataHolder.putSessionAttribute(SORT_TYPE_ATTR, DEFAULT_SORT_TYPE);
        }
        return sortType;
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
        int pageNumber = START_PAGE_NUMBER;
        if (requestDataHolder.isRequestParamContainsKey(PAGE_NUMBER)) {
            String pageNumberParam = requestDataHolder.getRequestParameter(PAGE_NUMBER);
            pageNumber = Integer.parseInt(pageNumberParam);
        }
        return pageNumber;


    }


}
