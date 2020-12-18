package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;
import java.util.Optional;

public class ChangeUserStatusCommand implements Command { //+

    private final static String USER_ID_PARAM = "userId";
    private final static String IS_ACTIVE_PARAM = "active";
    private final static String USERS_ATTR = "users";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    private final UserService userService;

    public ChangeUserStatusCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String userIdStr = requestDataHolder.getRequestParameter(USER_ID_PARAM);
        long userId = Long.parseLong(userIdStr);

        String isActiveParam = requestDataHolder.getRequestParameter(IS_ACTIVE_PARAM);
        boolean newActiveStatus = Boolean.parseBoolean(isActiveParam);

        Optional<User> userOpt = userService.changeUserStatus(userId, newActiveStatus);

        userOpt.ifPresent(user -> renewSessionData(user, requestDataHolder));

        String refForRedirect = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAM);
        return new CommandResult(refForRedirect, true);

    }


    private void renewSessionData(User changedUser, RequestDataHolder requestDataHolder) {
        long userId = changedUser.getId();
        List<User> users = (List<User>) requestDataHolder.getSessionAttribute(USERS_ATTR);
        if (users != null) {
            Optional<User> userFromSessionOpt = users.stream()
                    .filter(user -> user.getId() == userId)
                    .findFirst();

            if (userFromSessionOpt.isPresent()) {
                User userFromSession = userFromSessionOpt.get();
                int indexOfUser = users.indexOf(userFromSession);
                users.remove(userFromSession);
                users.add(indexOfUser, changedUser);
                requestDataHolder.putSessionAttribute(USERS_ATTR, users);
            }

        }
    }
}
