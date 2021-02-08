package com.github.gkttk.epam.logic.command.impl;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.logic.command.Command;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CancelDishCommand implements Command {

    private final static String BASKET_ATTR = "basket";
    private final static String DISH_ID_PARAM = "dishId";
    private final static String ORDER_COST_ATTR = "orderCost";
    private final static String CURRENT_PAGE_ATTR = "currentPage";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {
        List<Dish> basket = (List<Dish>) requestDataHolder.getSessionAttribute(BASKET_ATTR);

        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAM);
        long cancelledDishId = Long.parseLong(dishIdParam);

        Optional<Dish> canceledDish = basket.stream()
                .filter(dish -> dish.getId().equals(cancelledDishId))
                .findFirst();

        canceledDish.ifPresent(dish -> {
            basket.remove(dish);
            requestDataHolder.putSessionAttribute(BASKET_ATTR, basket);
            BigDecimal newOrderCost = getNewOrderCost(requestDataHolder, dish);
            requestDataHolder.putSessionAttribute(ORDER_COST_ATTR, newOrderCost);
        });

        String page = getRedirectPage(requestDataHolder, basket);

        return new CommandResult(page, true);
    }


    private BigDecimal getNewOrderCost(RequestDataHolder requestDataHolder, Dish cancelledDish) {
        BigDecimal dishCost = cancelledDish.getCost();
        BigDecimal orderCost = (BigDecimal) requestDataHolder.getSessionAttribute(ORDER_COST_ATTR);
        return orderCost.subtract(dishCost);
    }

    private String getRedirectPage(RequestDataHolder requestDataHolder, List<Dish> basket) {
        return basket.isEmpty() ? MENU_PAGE : (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_ATTR);
    }

}
