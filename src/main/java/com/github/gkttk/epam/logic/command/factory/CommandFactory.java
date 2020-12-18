package com.github.gkttk.epam.logic.command.factory;

import com.github.gkttk.epam.logic.command.*;
import com.github.gkttk.epam.logic.service.impl.*;
import com.github.gkttk.epam.logic.validator.UserPasswordValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//todo delete
public class CommandFactory {

    private final static Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public static Command createCommand(String commandName) {
        switch (commandName) {
            case "dishComments":{
                return new DishCommentsCommand(new CommentServiceImpl(), new UserCommentRatingServiceImpl());
            }

            case "cancelDish":{
                return new CancelDishCommand();
            }
            case "saveOrder":{
                return new SaveOrderCommand(new OrderServiceImpl());
            }
            case "comments": {
                return new CommentsPageCommand(new CommentServiceImpl(), new UserCommentRatingServiceImpl());
            }
            case "myOrders": {
                return new MyOrdersPageCommand(new OrderServiceImpl());
            }
            case "menu": {
                return new MenuPageCommand();
            }
            case "makeOrderCommand": {//formorder
                return new MakeOrderCommand();
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
                return new UsersPageCommand(new UserServiceImpl());
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
