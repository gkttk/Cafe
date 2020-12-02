package com.github.gkttk.epam.logic.command.factory;

import com.github.gkttk.epam.connection.ConnectionPool;
import com.github.gkttk.epam.dao.UserDaoImpl;
import com.github.gkttk.epam.dao.mappers.UserRowMapper;
import com.github.gkttk.epam.dao.parsers.UserParser;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.LocaleCommand;
import com.github.gkttk.epam.logic.command.LoginCommand;
import com.github.gkttk.epam.logic.service.UserServiceImpl;

public class CommandFactory {

    public static Command createCommand(String commandName) {
        switch (commandName) {

            case "locale": {
                return new LocaleCommand();
            }
            case "login":{
                return new LoginCommand(new UserServiceImpl(
                        new UserDaoImpl(ConnectionPool.getInstance().getConnection(),//todo !
                        new UserRowMapper(),
                        new UserParser())));
            }
            default: {
                //todo log
                throw new IllegalArgumentException("Given command " + commandName + " is not supported.");
            }
        }


    }

}
