package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;

import java.util.List;

public class SortDishesCommand implements Command {
    private final static String DISH_TYPE_PARAMETER = "dishType";
    private final static String DISHES_ATTRIBUTE = "dishes";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final DishService dishService;

    public SortDishesCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {

        String dishTypeStr = requestDataHolder.getRequestParameter(DISH_TYPE_PARAMETER);

        List<Dish> dishes;
        if (dishTypeStr != null){
            DishTypes dishType = DishTypes.valueOf(dishTypeStr);
            dishes = dishService.getDishesByType(dishType);
        }else {
            dishes = dishService.getAllDishes();
        }

        requestDataHolder.putSessionAttribute(DISHES_ATTRIBUTE, dishes);

        return new CommandResult(MENU_PAGE, true);
    }
}
