package com.github.gkttk.epam.controller;

import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.factory.CommandFactory;
import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {

    private final static String PAGE_ATTRIBUTE = "redirectPage";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(PAGE_ATTRIBUTE);
        try {
            request.getRequestDispatcher(page).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();//todo
        } catch (IOException e) {
            e.printStackTrace();  //todo
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String commandName = request.getParameter("command");
        try {
                Command command = CommandFactory.createCommand(commandName);
                CommandResult commandResult = command.execute(request, response);
                if (commandResult.isRedirect()) {
                    String url = commandResult.getUrl();
                    HttpSession session = request.getSession();
                    session.setAttribute(PAGE_ATTRIBUTE, url);
                    response.sendRedirect(request.getRequestURL().toString());
                } else {
                    request.getRequestDispatcher(commandResult.getUrl()).forward(request, response);
                }

        } catch (IOException exception) {
            exception.printStackTrace();//todo log
        } catch (ServletException exception) {
            exception.printStackTrace();//todo log
        }
    }




}
