package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.CommentService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.enums.CommentSortTypes;

import java.util.List;

public class SortCommentsCommand implements Command {

    private final CommentService commentService;

    private final static String COMMENTS_PAGE = "/WEB-INF/view/comment_page.jsp";
    private final static String COMMENTS_ATTR = "dishComments";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String PAGE_NUMBER_PARAM = "pageNumber";
    private final static String DISH_ID_ATTR = "dishId";
    private final static String SORT_TYPE_PARAM = "sortType";

    public SortCommentsCommand(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        String currentPageParam = requestDataHolder.getRequestParameter(PAGE_NUMBER_PARAM);
        int currentPage = Integer.parseInt(currentPageParam);

        long dishId = (long) requestDataHolder.getSessionAttribute(DISH_ID_ATTR);

        String sortTypeParam = requestDataHolder.getRequestParameter(SORT_TYPE_PARAM);
        CommentSortTypes sortType = CommentSortTypes.valueOf(sortTypeParam);
        requestDataHolder.putSessionAttribute(SORT_TYPE_PARAM, sortType);

        List<CommentInfo> comments = commentService.getAllByDishIdPagination(dishId, currentPage, sortType);

        requestDataHolder.putSessionAttribute(COMMENTS_ATTR, comments);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, COMMENTS_PAGE);
        return new CommandResult(COMMENTS_PAGE, true);
    }


}
