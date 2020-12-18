package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddToBasketCommand implements Command {

    private final static String BASKET_ATTRIBUTE = "basket";
    private final static String DISH_ID_PARAMETER = "dishId";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";
    private final DishService dishService;

    public AddToBasketCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {
        List<Dish> basket = (List<Dish>) requestDataHolder.getSessionAttribute(BASKET_ATTRIBUTE);

        if (basket == null) {
            basket = new ArrayList<>();
        }

        String dishIdStr = requestDataHolder.getRequestParameter(DISH_ID_PARAMETER);
        long dishId = Long.parseLong(dishIdStr);

        Optional<Dish> dishOpt = dishService.getDishById(dishId);

        if (dishOpt.isPresent()) {
            Dish dish = dishOpt.get();
            basket.add(dish);
        }

        requestDataHolder.putSessionAttribute(BASKET_ATTRIBUTE, basket);

        return new CommandResult(MENU_PAGE, true);
    }
}
