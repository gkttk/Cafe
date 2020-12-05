package com.github.gkttk.epam.logic.command.factory;

import com.github.gkttk.epam.logic.command.*;
import com.github.gkttk.epam.logic.service.impl.DishServiceImpl;
import com.github.gkttk.epam.logic.service.impl.UserServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

    private final static Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public static Command createCommand(String commandName) {
        switch (commandName) {
            case "makeOrderCommand":{
                return new MakeOrderCommand(new DishServiceImpl());
            }
            case "registration": {
                return new RegistrationCommand(new UserServiceImpl());
            }
            case "registration_page": { //todo
                return new RegistrationGetPageCommand();
            }
            case "changeStatus": {
                return new ChangeUserStatusCommand(new UserServiceImpl());
            }
            case "users": {
                return new UsersCommand(new UserServiceImpl());
            }
            case "home": {
                return new HomeCommand();
            }
            case "logout": {
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
