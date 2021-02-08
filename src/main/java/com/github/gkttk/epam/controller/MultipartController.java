package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
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
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Controller for images.
 */
public class MultipartController extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(MultipartController.class);
    private final static String FILE_ATTR = "file";
    private final static String TAIL_FOR_REDIRECT = "/controller?command=REDIRECT";
    private final static String COMMAND_PARAM = "command";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDataHolder requestDataHolder = new RequestDataHolder(request);
        try {
            Part file = request.getPart(FILE_ATTR);
            if (file == null) {
                LOGGER.info("There is no file in request");
                response.sendRedirect(getServletContext().getContextPath() + TAIL_FOR_REDIRECT);
            }
            requestDataHolder.putRequestAttribute(FILE_ATTR, file);
            Command command = getCommand(requestDataHolder);
            CommandResult commandResult = command.execute(requestDataHolder);
            requestDataHolder.fillRequest(request);
            String url = commandResult.getUrl();
            if (commandResult.isRedirect()) {
                if (!requestDataHolder.isSessionValid()) {
                    request.getSession().invalidate();
                }
                response.sendRedirect(getServletContext().getContextPath() + TAIL_FOR_REDIRECT);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (ServletException e) {
            LOGGER.warn("Can't forward from doPost()", e);
            response.sendError(500);
        } catch (ServiceException e) {
            LOGGER.warn("ServiceException has occurred", e);
            response.sendError(500);
        } catch (IOException e) {
            LOGGER.warn("IOException has occurred", e);
            response.sendError(500);
        }
    }

    private Command getCommand(RequestDataHolder requestDataHolder) throws ServletException {
        String commandName = requestDataHolder.getRequestParameter(COMMAND_PARAM);
        if (commandName == null) {
            throw new ServletException("Can't find command name in RequestDataHandler");
        }
        Commands commandEnum = Commands.valueOf(commandName);
        return commandEnum.getCommand();
    }


}
