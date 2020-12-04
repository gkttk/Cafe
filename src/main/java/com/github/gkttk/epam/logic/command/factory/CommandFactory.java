package com.github.gkttk.epam.logic.command.factory;

import com.github.gkttk.epam.connection.ConnectionPool;
import com.github.gkttk.epam.connection.ConnectionProxy;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.LocaleCommand;
import com.github.gkttk.epam.logic.command.LoginCommand;
import com.github.gkttk.epam.logic.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

    private final static Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public static Command createCommand(String commandName) {
        switch (commandName) {
            case "locale": {
                return new LocaleCommand();
            }
            case "login": {
                return new LoginCommand(new UserServiceImpl(new DaoHelperFactory()));
            }
            default: {
                LOGGER.error("Given command:{} is not supported.", commandName);
                throw new IllegalArgumentException("Given command " + commandName + " is not supported.");
            }
        }
    }



}
