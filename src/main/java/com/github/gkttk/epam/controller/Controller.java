package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.factory.CommandFactory;
import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        process(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        String commandName = request.getParameter("command");
        try {
            if (commandName != null) {
                Command command = CommandFactory.createCommand(commandName);
                CommandResult commandResult = command.execute(request, response);
                if (commandResult.isRedirect()) {
                    response.sendRedirect(commandResult.getUrl());
                } else {
                    request.getRequestDispatcher(commandResult.getUrl()).forward(request, response);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();//todo log
        } catch (ServletException exception) {
            exception.printStackTrace();//todo log
        }
    }
}
