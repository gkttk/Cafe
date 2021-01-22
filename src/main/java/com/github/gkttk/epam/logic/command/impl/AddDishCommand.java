package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.interpreter.Base64Encoder;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.logic.validator.Validator;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public class AddDishCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(AddDishCommand.class);

    private final static String MESSAGE_ATTR = "message";
    private final static String ERROR_MESSAGE_DISH_NAME = "error.message.dish.name";
    private final static String ERROR_MESSAGE_DISH_COST = "error.message.dish.cost";

    private final static String FILE_ATTR = "file";
    private final static String DISH_NAME_PARAM = "name";
    private final static String DISH_COST_PARAM = "cost";
    private final static String DISH_TYPE_PARAM = "type";

    private final static String DISHES_ATTR = "dishes";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    private final DishService dishService;
    private final Validator dishNameValidator;
    private final Validator dishCostValidator;

    public AddDishCommand(DishService dishService, Validator dishNameValidator, Validator dishCostValidator) {
        this.dishService = dishService;
        this.dishNameValidator = dishNameValidator;
        this.dishCostValidator = dishCostValidator;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        try {
            String dishName = requestDataHolder.getRequestParameter(DISH_NAME_PARAM);
            boolean isDishNameValid = dishNameValidator.validate(dishName);

            if (!isDishNameValid) {
                LOGGER.info("Incorrect  dishNameParam format: {}", dishName);
                requestDataHolder.putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_DISH_NAME);
                return new CommandResult(MENU_PAGE, false);
            }

            String costParam = requestDataHolder.getRequestParameter(DISH_COST_PARAM);
            boolean isDishCostValid = dishCostValidator.validate(costParam);

            if (!isDishCostValid) {
                LOGGER.info("Incorrect  costParam format: {}", costParam);
                requestDataHolder.putRequestAttribute(MESSAGE_ATTR, ERROR_MESSAGE_DISH_COST);
                return new CommandResult(MENU_PAGE, false);
            }

            BigDecimal dishCost = new BigDecimal(costParam);

            String typeParam = requestDataHolder.getRequestParameter(DISH_TYPE_PARAM);
            DishType dishType = DishType.valueOf(typeParam);

            Part dishImg = (Part) requestDataHolder.getRequestAttribute(FILE_ATTR);
            InputStream inputStream = dishImg.getInputStream();

            String byteString = Base64Encoder.encode(inputStream);

            dishService.addDish(dishName, dishCost, dishType,byteString);

            renewSession(requestDataHolder);
        } catch (IOException e) {
            throw new ServiceException("Can't get input stream from part", e);//todo
        }

        return new CommandResult(MENU_PAGE, true);

    }


    private void renewSession(RequestDataHolder requestDataHolder) throws ServiceException {
        List<Dish> dishes = dishService.getAllEnabled();
        requestDataHolder.putSessionAttribute(DISHES_ATTR, dishes);
    }

}
