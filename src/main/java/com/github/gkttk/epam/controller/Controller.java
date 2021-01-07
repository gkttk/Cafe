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

public class Controller extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(Controller.class);
    private final static String CURRENT_PAGE_ATTR = "currentPage";
    private final static ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    //todo throw IOException
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(CURRENT_PAGE_ATTR);
        try {
            request.getRequestDispatcher(page).forward(request, response);
        } catch (ServletException e) {
            LOGGER.warn("Can't forward from doGet()", e);
            response.sendError(500);
        } catch (IOException e) {
            LOGGER.warn("Can't forward/redirect from doGet()", e);
            response.sendError(500);
        }
    }

    //todo throw IOException
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestDataHolder requestDataHolder = new RequestDataHolder(request);
        try {
            Command command = getCommand(requestDataHolder);
            CommandResult commandResult = command.execute(requestDataHolder);
            requestDataHolder.fillRequest(request);
            String url = commandResult.getUrl();
            if (commandResult.isRedirect()) {
                if (!requestDataHolder.isSessionValid()) {
                    request.getSession().invalidate();
                }//todo invalidate only for redirect
                request.getSession().setAttribute(CURRENT_PAGE_ATTR, url);
                response.sendRedirect(request.getRequestURL().toString());
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }

        } catch (ServiceException e) {
            LOGGER.warn("ServiceException has occurred", e);
            response.sendError(500);
        } catch (ServletException e) {
            LOGGER.warn("Can't forward from doPost()", e);
            response.sendError(500);
        } catch (IOException e) {
            LOGGER.warn("Can't forward/redirect from doPost()", e);
            response.sendError(500);
        }
    }

    private Command getCommand(RequestDataHolder requestDataHolder) throws ServletException {
        String commandName = requestDataHolder.getRequestParameter("command");
        if (commandName == null) {
            throw new ServletException("Can't find command name in RequestDataHandler");
        }
        Commands commandEnum = Commands.valueOf(commandName);
        return commandEnum.getCommand();
    }


    @Override
    public void destroy() {
        try {
            CONNECTION_POOL.destroy();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Couldn't close some connection", e);
        }
    }
}
