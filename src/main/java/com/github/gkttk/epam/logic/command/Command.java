package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.model.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    CommandResult execute(HttpServletRequest request, HttpServletResponse response);


}
