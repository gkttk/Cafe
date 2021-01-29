package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddToBasketCommand implements Command {

    private final DishService dishService;
    private final static String BASKET_ATTR = "basket";
    private final static String DISH_ID_PARAM = "dishId";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    public AddToBasketCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        List<Dish> basket = getBasket(requestDataHolder);

        addDishToBasket(requestDataHolder, basket);

        requestDataHolder.putSessionAttribute(BASKET_ATTR, basket);

        return new CommandResult(MENU_PAGE, true);
    }

    private List<Dish> getBasket(RequestDataHolder requestDataHolder) {
        List<Dish> basket = (List<Dish>) requestDataHolder.getSessionAttribute(BASKET_ATTR);
        return basket == null ? new ArrayList<>() : basket;
    }

    private void addDishToBasket(RequestDataHolder requestDataHolder, List<Dish> basket) throws ServiceException {
        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAM);
        long dishId = Long.parseLong(dishIdParam);
        Optional<Dish> dishOpt = dishService.getDishById(dishId);
        dishOpt.ifPresent(basket::add);
    }


}
