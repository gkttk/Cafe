package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.logic.validator.factory.ValidatorFactory;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;

import java.math.BigDecimal;
import java.util.Optional;

public class AddMoneyCommand implements Command {

    private final UserService userService;


    private final static String AUTH_USER_ATTR = "authUser";
    private final static String MONEY_PARAM = "money";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    public AddMoneyCommand(UserService userService) {
        this.userService = userService;

    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String moneyParam = requestDataHolder.getRequestParameter(MONEY_PARAM);
        Validator moneyValidator = ValidatorFactory.getMoneyValidator();
        boolean isParamValid = moneyValidator.validate(moneyParam);
        if (!isParamValid){
            return new CommandResult(CURRENT_PAGE_PARAM, false);
        }

        BigDecimal money = new BigDecimal(moneyParam);

        User authUser = (User) requestDataHolder.getSessionAttribute(AUTH_USER_ATTR);
        long userId = authUser.getId();

        userService.addMoney(userId, money);

        renewSessionData(requestDataHolder, userId);
        String redirectPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAM);
        return new CommandResult(redirectPage, true);
    }

    private void renewSessionData(RequestDataHolder requestDataHolder, long userId) throws ServiceException {
        Optional<User> userOpt = userService.getById(userId);
        userOpt.ifPresent(user -> requestDataHolder.putSessionAttribute(AUTH_USER_ATTR, user));

    }


}