package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.UserService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class AddMoneyCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(AddMoneyCommand.class);
    private final UserService userService;
    private final Validator moneyValidator;

    private final static String AUTH_USER_ATTR = "authUser";
    private final static String MONEY_PARAM = "money";
    private final static String CURRENT_PAGE_PARAM = "currentPage";

    public AddMoneyCommand(UserService userService, Validator moneyValidator) {
        this.userService = userService;
        this.moneyValidator = moneyValidator;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String moneyParam = requestDataHolder.getRequestParameter(MONEY_PARAM);
        boolean isParamValid = moneyValidator.validate(moneyParam);
        if (!isParamValid) {
            LOGGER.info("Incorrect money format: {}", moneyParam);
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