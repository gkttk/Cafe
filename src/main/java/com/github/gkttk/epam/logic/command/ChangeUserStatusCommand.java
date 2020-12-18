package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;

import java.util.List;
import java.util.Optional;

public class ChangeUserStatusCommand implements Command {

    private final static String IS_ACTIVE_PARAMETER = "active";
    private final static String USER_ID_PARAMETER = "userId";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final static String USERS_ATTRIBUTE_KEY = "users";
    private final UserService userService;

    public ChangeUserStatusCommand(UserService userService) {
        this.userService = userService;
    }

    //todo refactor
    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String userIdParameter = requestDataHolder.getRequestParameter(USER_ID_PARAMETER);
        long userId = Long.parseLong(userIdParameter);

        String isActiveParameter = requestDataHolder.getRequestParameter(IS_ACTIVE_PARAMETER);
        boolean isActive = Boolean.parseBoolean(isActiveParameter);


        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent() && userOptional.get().isActive() != isActive) {
            userService.setUserStatus(userId, isActive);
            List<User> attribute = (List<User>) requestDataHolder.getSessionAttribute(USERS_ATTRIBUTE_KEY);
            if (attribute != null) {
                Optional<User> changingUserOpt = attribute.stream().filter(user -> user.getId() == userId).findFirst();
                if (changingUserOpt.isPresent()) {
                    User changingUser = changingUserOpt.get();
                    int index = attribute.indexOf(changingUser);
                    attribute.remove(changingUser);
                    Optional<User> updatedUser = userService.getUserById(userId);
                    attribute.add(index, updatedUser.get());
                    requestDataHolder.putSessionAttribute(USERS_ATTRIBUTE_KEY, attribute);
                }
            }
        }

        String refForRedirect = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAMETER);

        return new CommandResult(refForRedirect, true);
    }
}
