package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.service.impl.UserServiceImpl;
import com.github.gkttk.epam.model.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

@WebServlet(urlPatterns = "/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class MultipartController extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(MultipartController.class);
    private final static String PART_ATTRIBUTE_KEY = "newAvatar";
    private final static String AUTH_USER_ATTRIBUTE_KEY = "authUser";
    private final static String TAIL_FOR_REDIRECT = "/controller";

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Part newAvatar = request.getPart(PART_ATTRIBUTE_KEY);

            if (newAvatar == null) {
                response.sendRedirect(getServletContext().getContextPath() + TAIL_FOR_REDIRECT);
            }

            String byteString = getByteString(newAvatar);

            User authUser = (User) request.getSession().getAttribute(AUTH_USER_ATTRIBUTE_KEY);
            Long userId = authUser.getId();
            userService.changeAvatar(authUser, byteString);
            renewSession(userId, request);
            response.sendRedirect(getServletContext().getContextPath() + TAIL_FOR_REDIRECT);

        } catch (ServletException e) {
            LOGGER.warn("Can't forward from doPost()", e);
        } catch (ServiceException e) {
            LOGGER.warn("ServiceException has occurred", e);
        } catch (IOException e) {
            LOGGER.warn("IOException has occurred", e);
        }


    }


    private String getByteString(Part part) throws IOException {
        try (InputStream inputStream = part.getInputStream()) {
            int availableBytes = inputStream.available();
            byte[] buffer = new byte[availableBytes];
            inputStream.read(buffer);
            return Base64.getEncoder().encodeToString(buffer);

        }
    }

    private void renewSession(long userId, HttpServletRequest request) throws ServiceException {
        Optional<User> userByIdOpt = userService.getById(userId);
        userByIdOpt.ifPresent(user -> request.getSession().setAttribute(AUTH_USER_ATTRIBUTE_KEY, user));
    }


}
