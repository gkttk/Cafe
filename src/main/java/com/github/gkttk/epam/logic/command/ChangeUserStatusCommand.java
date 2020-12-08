package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String userIdParameter = request.getParameter(USER_ID_PARAMETER);
        long userId = Long.parseLong(userIdParameter);

        String isActiveParameter = request.getParameter(IS_ACTIVE_PARAMETER);
        boolean isActive = Boolean.parseBoolean(isActiveParameter);

        HttpSession session = request.getSession();

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent() && userOptional.get().isActive() != isActive) {
            userService.setUserStatus(userId, isActive);
            List<User> attribute = (List<User>) session.getAttribute(USERS_ATTRIBUTE_KEY);
            if (attribute != null) {
                Optional<User> first = attribute.stream().filter(user -> user.getId() == userId).findFirst();
                if (first.isPresent()) {
                    User user = first.get();
                    int index = attribute.indexOf(user);
                    attribute.remove(user);

                    Optional<User> updatedUser = userService.getUserById(userId);
                    attribute.add(index, updatedUser.get());
                    session.setAttribute(USERS_ATTRIBUTE_KEY, attribute);
                }
              /*  attribute.removeIf(user -> user.getId() == userId);
                Optional<User> updatedUser = userService.getUserById(userId);
                attribute.add(updatedUser.get());
                session.setAttribute(USERS_ATTRIBUTE_KEY, attribute);*/
            }
        }


        String refForRedirect = (String) session.getAttribute(CURRENT_PAGE_PARAMETER);

        return new CommandResult(refForRedirect, true);
    }
}
