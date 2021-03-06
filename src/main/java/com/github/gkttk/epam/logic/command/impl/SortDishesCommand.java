package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;

import java.util.List;

public class SortDishesCommand implements Command {

    private final DishService dishService;

    private final static String DISH_TYPE_PARAM = "dishType";
    private final static String DISHES_ATTR = "dishes";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    public SortDishesCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String dishTypeParam = requestDataHolder.getRequestParameter(DISH_TYPE_PARAM);

        List<Dish> dishes;
        if (dishTypeParam != null) {
            DishType dishType = DishType.valueOf(dishTypeParam);
            dishes = dishService.getByType(dishType);
        } else {
            dishes = dishService.getAllEnabled();
        }

        requestDataHolder.putSessionAttribute(DISHES_ATTR, dishes);

        return new CommandResult(MENU_PAGE, true);
    }
}
