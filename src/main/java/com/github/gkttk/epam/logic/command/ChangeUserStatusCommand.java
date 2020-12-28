package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.dto.UserInfo;

import java.util.List;
import java.util.Optional;

public class ChangeUserStatusCommand implements Command { //+

    private final UserService userService;
    private final static String USER_ID_PARAM = "userId";
    private final static String IS_BLOCKED_PARAM = "blocked";
    private final static String USERS_ATTR = "users";
    private final static String CURRENT_PAGE_PARAM = "currentPage";


    public ChangeUserStatusCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String userIdParam = requestDataHolder.getRequestParameter(USER_ID_PARAM);
        long userId = Long.parseLong(userIdParam);

        String isBlockedParam = requestDataHolder.getRequestParameter(IS_BLOCKED_PARAM);
        boolean isBlocked = Boolean.parseBoolean(isBlockedParam);

        Optional<UserInfo> userOpt = userService.changeUserStatus(userId, isBlocked);

        userOpt.ifPresent(user -> renewSession(requestDataHolder, user));

        String redirectPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAM);
        return new CommandResult(redirectPage, true);

    }

    private void renewSession(RequestDataHolder requestDataHolder, UserInfo changedUser) {
        long userId = changedUser.getId();
        List<UserInfo> users = (List<UserInfo>) requestDataHolder.getSessionAttribute(USERS_ATTR);
        if (users != null) {
            Optional<UserInfo> userFromSessionOpt = users.stream()
                    .filter(user -> user.getId() == userId)
                    .findFirst();

            if (userFromSessionOpt.isPresent()) {
                UserInfo userFromSession = userFromSessionOpt.get();
                int indexOfUser = users.indexOf(userFromSession);
                users.remove(userFromSession);
                users.add(indexOfUser, changedUser);
                requestDataHolder.putSessionAttribute(USERS_ATTR, users);
            }
        }
    }
}
