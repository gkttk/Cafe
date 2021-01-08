package com.github.gkttk.epam.logic.command.enums;

import com.github.gkttk.epam.logic.command.*;
import com.github.gkttk.epam.logic.service.impl.*;
import com.github.gkttk.epam.logic.validator.DataValidator;
import com.github.gkttk.epam.logic.validator.MoneyValidator;

public enum Commands {
    LOGIN(new LoginCommand(new UserServiceImpl(), new DishServiceImpl())),
    LOCALE(new LocaleCommand()),
    LOGOUT(new LogoutCommand()),
    HOME(new HomeCommand()),
    USERS(new UsersPageCommand(new UserServiceImpl())),
    CHANGE_STATUS(new ChangeUserStatusCommand(new UserServiceImpl())),
    REGISTRATION_PAGE(new RegistrationGetPageCommand()),
    REGISTRATION(new RegistrationCommand(new UserServiceImpl())),
    FORM_ORDER(new MakeOrderCommand()),
    MENU(new MenuPageCommand()),
    MY_ORDERS(new MyOrdersPageCommand(new OrderServiceImpl())),
    SAVE_ORDER(new SaveOrderCommand(new OrderServiceImpl())),
    CANCEL_DISH(new CancelDishCommand()),
    RATE_COMMENT(new RateCommentCommand(new CommentServiceImpl(), new UserCommentRatingServiceImpl())),
    SORT_DISHES(new SortDishesCommand(new DishServiceImpl())),
    TO_BASKET(new AddToBasketCommand(new DishServiceImpl())),
    DISH_COMMENTS(new DishCommentsCommand(new CommentServiceImpl(), new UserCommentRatingServiceImpl())),
    ADD_COMMENT(new AddCommentCommand(new CommentServiceImpl())),
    TAKE_ORDER(new TakeOrderCommand(new OrderServiceImpl(), new UserServiceImpl())),
    CANCEL_ORDER(new CancelOrderCommand(new OrderServiceImpl(), new UserServiceImpl())),
    SORT_USERS(new SortUsersCommand(new UserServiceImpl())),
    CHANGE_POINTS(new ChangeUserPointsCommand(new UserServiceImpl())),
    UPDATE_COMMENT(new UpdateCommentCommand(new CommentServiceImpl())),
    DELETE_COMMENT(new DeleteCommentCommand(new CommentServiceImpl())),
    SORT_COMMENTS(new SortCommentsCommand(new CommentServiceImpl())),
    ORDER_HISTORY(new OrderHistoryCommand(new OrderServiceImpl())),
    ADD_MONEY(new AddMoneyCommand(new UserServiceImpl())),
    CHANGE_AVATAR(new ChangeAvatarCommand(new UserServiceImpl()));


    Commands(Command command) {
        this.command = command;
    }

    private Command command;

    public Command getCommand() {
        return command;
    }

}
