package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.List;

public class DisableDishCommand implements Command {

    private final DishService dishService;
    private final static String DISH_ID_PARAM = "dishId";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final static String DISHES_ATTR = "dishes";

    public DisableDishCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAM);
        long dishId = Long.parseLong(dishIdParam);
        dishService.disableDish(dishId);

        renewSession(requestDataHolder);

        return new CommandResult(MENU_PAGE, true);
    }


    private void renewSession(RequestDataHolder requestDataHolder) throws ServiceException {
        List<Dish> dishes = dishService.getAllEnabled();
        requestDataHolder.putSessionAttribute(DISHES_ATTR, dishes);
    }
}
