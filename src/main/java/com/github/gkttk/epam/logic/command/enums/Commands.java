package com.github.gkttk.epam.logic.command.enums;

import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactoryImpl;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.command.impl.*;
import com.github.gkttk.epam.logic.service.impl.*;
import com.github.gkttk.epam.logic.validator.*;

public enum Commands {
    LOGIN(new LoginCommand(new UserServiceImpl(new DaoHelperFactoryImpl()), new DishServiceImpl(new DaoHelperFactoryImpl()))),
    LOCALE(new LocaleCommand()),
    LOGOUT(new LogoutCommand()),
    HOME(new HomeCommand()),
    USERS(new UsersPageCommand(new UserServiceImpl(new DaoHelperFactoryImpl()))),
    CHANGE_STATUS(new ChangeUserStatusCommand(new UserServiceImpl(new DaoHelperFactoryImpl()))),
    REGISTRATION_PAGE(new RegistrationGetPageCommand()),
    REGISTRATION(new RegistrationCommand(new UserServiceImpl(new DaoHelperFactoryImpl()), new UserLoginValidator(new DaoHelperFactoryImpl()), new UserPasswordValidator())),
    FORM_ORDER(new MakeOrderCommand()),
    MENU(new MenuPageCommand()),
    MY_ORDERS(new MyOrdersPageCommand(new OrderServiceImpl(new DaoHelperFactoryImpl()))),
    SAVE_ORDER(new SaveOrderCommand(new OrderServiceImpl(new DaoHelperFactoryImpl()), new DataValidator())),
    CANCEL_DISH(new CancelDishCommand()),
    RATE_COMMENT(new RateCommentCommand(new CommentServiceImpl(new DaoHelperFactoryImpl()), new UserCommentRatingServiceImpl(new DaoHelperFactoryImpl()))),
    SORT_DISHES(new SortDishesCommand(new DishServiceImpl(new DaoHelperFactoryImpl()))),
    TO_BASKET(new AddToBasketCommand(new DishServiceImpl(new DaoHelperFactoryImpl()))),
    DISH_COMMENTS(new DishCommentsCommand(new CommentServiceImpl(new DaoHelperFactoryImpl()), new UserCommentRatingServiceImpl(new DaoHelperFactoryImpl()))),
    ADD_COMMENT(new AddCommentCommand(new CommentServiceImpl(new DaoHelperFactoryImpl()), new CommentValidator())),
    TAKE_ORDER(new TakeOrderCommand(new OrderServiceImpl(new DaoHelperFactoryImpl()), new UserServiceImpl(new DaoHelperFactoryImpl()))),
    CANCEL_ORDER(new CancelOrderCommand(new OrderServiceImpl(new DaoHelperFactoryImpl()), new UserServiceImpl(new DaoHelperFactoryImpl()))),
    SORT_USERS(new SortUsersCommand(new UserServiceImpl(new DaoHelperFactoryImpl()))),
    CHANGE_POINTS(new ChangeUserPointsCommand(new UserServiceImpl(new DaoHelperFactoryImpl()))),
    UPDATE_COMMENT(new UpdateCommentCommand(new CommentServiceImpl(new DaoHelperFactoryImpl()))),
    DELETE_COMMENT(new DeleteCommentCommand(new CommentServiceImpl(new DaoHelperFactoryImpl()))),
    SORT_COMMENTS(new SortCommentsCommand(new CommentServiceImpl(new DaoHelperFactoryImpl()))),
    ORDER_HISTORY(new OrderHistoryCommand(new OrderServiceImpl(new DaoHelperFactoryImpl()))),
    ADD_MONEY(new AddMoneyCommand(new UserServiceImpl(new DaoHelperFactoryImpl()), new MoneyValidator())),
    CHANGE_AVATAR(new ChangeAvatarCommand(new UserServiceImpl(new DaoHelperFactoryImpl()))),
    ADD_DISH(new AddDishCommand(new DishServiceImpl(new DaoHelperFactoryImpl()), new DishNameValidator(), new DishCostValidator())),
    REMOVE_DISH(new DisableDishCommand(new DishServiceImpl(new DaoHelperFactoryImpl())));


    Commands(Command command) {
        this.command = command;
    }

    private Command command;

    public Command getCommand() {
        return command;
    }

}
