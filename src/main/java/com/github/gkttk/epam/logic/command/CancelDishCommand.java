package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.handler.RequestDataHolder;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CancelDishCommand implements Command {

    private final static String CURRENT_PAGE_ATTRIBUTE = "currentPage";
    private final static String ORDER_DISHES_ATTRIBUTE = "orderDishes";
    private final static String DISH_ID_PARAMETER = "dishId";
    private final static String ORDER_COST_ATTRIBUTE = "orderCost";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder){

        List<Dish> orderDishes = (List<Dish>) requestDataHolder.getSessionAttribute(ORDER_DISHES_ATTRIBUTE);

        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAMETER);
        long dishIdForCancel = Long.parseLong(dishIdParam);

        Optional<Dish> dishForDeleting = orderDishes.stream()
                .filter(dish -> dish.getId() == dishIdForCancel).findFirst();

        if(dishForDeleting.isPresent()){
            Dish dish = dishForDeleting.get();
            BigDecimal dishCost = dish.getCost();
            orderDishes.remove(dish);
            requestDataHolder.putSessionAttribute(ORDER_DISHES_ATTRIBUTE, orderDishes);
            BigDecimal orderCost = (BigDecimal) requestDataHolder.getSessionAttribute(ORDER_COST_ATTRIBUTE);
            BigDecimal newOrderCost = orderCost.subtract(dishCost);
            requestDataHolder.putSessionAttribute(ORDER_COST_ATTRIBUTE, newOrderCost);
        }


        String page = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_ATTRIBUTE);
        if (orderDishes.isEmpty()){
            page = MENU_PAGE;
        }

        return new CommandResult(page, true);
    }
}
