package com.github.gkttk.epam.logic.command.enums;

import com.github.gkttk.epam.logic.command.*;
import com.github.gkttk.epam.logic.service.impl.*;

public enum Commands {
    LOGIN(new LoginCommand(new UserServiceImpl(), new DishServiceImpl())),
    LOCALE(new LocaleCommand()),
    LOGOUT(new LogoutCommand()),
    HOME(new HomeCommand()),
    USERS(new UsersCommand(new UserServiceImpl())),
    CHANGE_STATUS(new ChangeUserStatusCommand(new UserServiceImpl())),
    REGISTRATION_PAGE(new RegistrationGetPageCommand()),
    REGISTRATION(new RegistrationCommand(new UserServiceImpl())),
    FORM_ORDER(new MakeOrderCommand()),
    MENU(new MenuPageCommand()),
    MY_ORDERS(new MyOrdersPageCommand(new OrderServiceImpl())),
    COMMENTS(new CommentsPageCommand(new CommentServiceImpl(), new UserCommentRatingServiceImpl())),
    SAVE_ORDER(new SaveOrderCommand(new OrderServiceImpl())),
    CANCEL_DISH(new CancelDishCommand()),
    RATE_COMMENT(new RateCommentCommand(new CommentServiceImpl(), new UserCommentRatingServiceImpl())),
    SORT_DISHES(new SortDishesCommand(new DishServiceImpl())),
    TO_BASKET(new AddToBasketCommand(new DishServiceImpl())),
    DISH_COMMENTS(new DishCommentsCommand(new CommentServiceImpl(), new UserCommentRatingServiceImpl())),
    ADD_COMMENT(new AddCommentCommand(new CommentServiceImpl())),
    TAKE_ORDER(new TakeOrderCommand(new OrderServiceImpl()));


    Commands(Command command) {
        this.command = command;
    }

    private Command command;

    public Command getCommand(){
        return command;
    }

}
