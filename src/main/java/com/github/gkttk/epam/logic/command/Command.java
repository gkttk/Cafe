package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.CommandResult;

/**
 * Common command interface. Gets RequestDataHolder with HttpServletRequest parameters and attributes.
 */
public interface Command {

    CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException;


}
