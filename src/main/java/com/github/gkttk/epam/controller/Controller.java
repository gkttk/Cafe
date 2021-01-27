package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.connection.ConnectionPool;
import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ConnectionPoolException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.enums.Commands;
import com.github.gkttk.epam.model.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Main controller.
 */
public class Controller extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(Controller.class);
    private final static ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private final static String MESSAGE_ATTR = "message";
    private final static String CURRENT_PAGE_ATTR = "currentPage";
    private final static String COMMAND_PARAM = "command";
    private final static String START_PAGE = "index.jsp";
    private final static String REDIRECT_TALE = "?command=REDIRECT";

    private void doCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDataHolder requestDataHolder = new RequestDataHolder(request);
        try {
            Command command = getCommand(requestDataHolder, response);
            CommandResult commandResult = command.execute(requestDataHolder);
            requestDataHolder.fillRequest(request);
            String url = commandResult.getUrl();
            if (commandResult.isRedirect()) {
                redirect(requestDataHolder, request, response, url);
            } else {
                forward(request, response, url);
            }
        } catch (ServiceException e) {
            LOGGER.warn("ServiceException has occurred", e);
            response.sendError(500);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(CURRENT_PAGE_ATTR);
        if (page == null) {
            page = START_PAGE;
            session.setAttribute(CURRENT_PAGE_ATTR, page);
        }
        String message = (String) session.getAttribute(MESSAGE_ATTR);
        if (message != null) {
            request.setAttribute(MESSAGE_ATTR, message);
            session.removeAttribute(MESSAGE_ATTR);
        }
        doCommand(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doCommand(request, response);

    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException ex) {
            LOGGER.warn("Can't forward from doPost()", ex);
            response.sendError(500);
        }
    }

    private void redirect(RequestDataHolder requestDataHolder, HttpServletRequest request, HttpServletResponse response,
                          String url) throws IOException {
        if (!requestDataHolder.isSessionValid()) {
            request.getSession().invalidate();
        }
        request.getSession().setAttribute(CURRENT_PAGE_ATTR, url);
        try {
            String redirectUrl = request.getRequestURL() + REDIRECT_TALE;
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            LOGGER.warn("Can't forward/redirect from doPost()", e);
            response.sendError(500);
        }
    }


    private Command getCommand(RequestDataHolder requestDataHolder, HttpServletResponse response) throws IOException {
        String commandName = requestDataHolder.getRequestParameter(COMMAND_PARAM);
        if (commandName == null) {
            LOGGER.warn("There is no command parameter in getCommand");
            response.sendError(500);
        }
        Commands commandEnum = Commands.valueOf(commandName);
        return commandEnum.getCommand();
    }


    @Override
    public void destroy() {
        try {
            CONNECTION_POOL.destroy();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Couldn't destroy connection pool in Controller", e);
        }
    }
}
