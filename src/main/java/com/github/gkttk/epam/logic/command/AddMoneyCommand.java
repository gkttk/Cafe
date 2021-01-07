package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserCommentRatingService;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.service.impl.CommentServiceImpl;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.CommentInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.entities.UserCommentRating;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddMoneyCommand implements Command {

    private final UserService userService;

    private final static String AUTH_USER_ATTR = "authUser";
    private final static String MONEY_PARAM = "money";
    private final static String CURRENT_PAGE_PARAM = "currentPage";


    public AddMoneyCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        String moneyParam = requestDataHolder.getRequestParameter(MONEY_PARAM);
        BigDecimal money = new BigDecimal(moneyParam);

        userService.addMoney(userId, money);

        renewSessionData(requestDataHolder, userId);
        String redirectPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAM);
        return new CommandResult(redirectPage, true);
    }

    private void renewSessionData(RequestDataHolder requestDataHolder, long userId) throws ServiceException {
        Optional<User> userOpt = userService.getById(userId);
        userOpt.ifPresent(user -> requestDataHolder.putSessionAttribute(AUTH_USER_ATTR, user));

    }


}