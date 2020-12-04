package com.github.gkttk.epam.logic.command.factory;

import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.LocaleCommand;
import com.github.gkttk.epam.logic.command.LoginCommand;
import com.github.gkttk.epam.logic.command.LogoutCommand;
import com.github.gkttk.epam.logic.service.impl.DishServiceImpl;
import com.github.gkttk.epam.logic.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

    private final static Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public static Command createCommand(String commandName) {
        switch (commandName) {
            case "logout":{
                return new LogoutCommand();
            }
            case "locale": {
                return new LocaleCommand();
            }
            case "login": {
                return new LoginCommand(new UserServiceImpl(), new DishServiceImpl());
            }
            default: {
                LOGGER.error("Given command:{} is not supported.", commandName);
                throw new IllegalArgumentException("Given command " + commandName + " is not supported.");
            }
        }
    }



}
