package com.github.gkttk.epam.logic.command.factory;

import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.LocaleCommand;

public class CommandFactory {

    public static Command createCommand(String commandName) {
        switch (commandName) {
            case "locale": {
                return new LocaleCommand();
            }
            default: {
                //todo log
                throw new IllegalArgumentException("Given command " + commandName + " is not supported.");
            }
        }


    }

}
