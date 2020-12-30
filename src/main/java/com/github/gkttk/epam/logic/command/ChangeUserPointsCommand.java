package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.UserInfo;

import java.util.List;

public class ChangeUserPointsCommand implements Command {

    private final UserService userService;

    private final static String USER_ID_PARAM = "userId";
    private final static String POINTS_PARAM = "points";
    private final static String IS_ADD_PARAM = "isAdd";
    private final static String USERS_ATTR = "users";
    private final static String USERS_PAGE = "/WEB-INF/view/users_page.jsp";


    public ChangeUserPointsCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String userIdParam = requestDataHolder.getRequestParameter(USER_ID_PARAM);
        long userId = Long.parseLong(userIdParam);

        String pointsParam = requestDataHolder.getRequestParameter(POINTS_PARAM);
        int points = Integer.parseInt(pointsParam);

        String isAddParam = requestDataHolder.getRequestParameter(IS_ADD_PARAM);
        boolean isAdd = Boolean.parseBoolean(isAddParam);

        userService.changePoints(userId, points, isAdd);

        List<UserInfo> users = userService.getAll();
        requestDataHolder.putSessionAttribute(USERS_ATTR, users);//todo mb renew session


        return new CommandResult(USERS_PAGE, true);


    }
}
