package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

public class ChangeAvatarCommand implements Command {
    private final static String FILE_ATTR = "file";
    private final static String AUTH_USER_ATTR = "authUser";
    private final static String MAIN_PAGE_ATTR = "index.jsp";
    private final static String CURRENT_PAGE_ATTR = "currentPage";
    private final UserService userService;

    public ChangeAvatarCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        try {
            Part newAvatar = (Part) requestDataHolder.getRequestAttribute(FILE_ATTR);
            String byteString = getByteString(newAvatar);
            User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
            Long userId = authUser.getId();
            userService.changeAvatar(authUser, byteString);
            renewSession(userId, requestDataHolder);
        } catch (IOException e) {
            throw new ServiceException("Can't get byte string", e);
        }
        String redirectPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_ATTR);
        return redirectPage != null ? new CommandResult(redirectPage, true) : new CommandResult(MAIN_PAGE_ATTR, true);

    }


    private String getByteString(Part part) throws IOException {
        try (InputStream inputStream = part.getInputStream()) {
            int availableBytes = inputStream.available();
            byte[] buffer = new byte[availableBytes];
            inputStream.read(buffer);
            return Base64.getEncoder().encodeToString(buffer);

        }
    }

    private void renewSession(long userId, RequestDataHolder requestDataHolder) throws ServiceException {
        Optional<User> userByIdOpt = userService.getById(userId);
        userByIdOpt.ifPresent(user -> requestDataHolder.putSessionAttribute(AUTH_USER_ATTR, user));
    }

}
