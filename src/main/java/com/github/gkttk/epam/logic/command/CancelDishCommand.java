package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CancelDishCommand implements Command {

    private final static String BASKET_ATTRIBUTE = "basket";
    private final static String DISH_ID_PARAMETER = "dishId";
    private final static String ORDER_COST_ATTRIBUTE = "orderCost";
    private final static String CURRENT_PAGE_ATTRIBUTE = "currentPage";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {

        List<Dish> basket = (List<Dish>) requestDataHolder.getSessionAttribute(BASKET_ATTRIBUTE);

        String dishIdParam = requestDataHolder.getRequestParameter(DISH_ID_PARAMETER);

        long dishIdForCancel = Long.parseLong(dishIdParam);

        Optional<Dish> dishForDeleteOpt = basket.stream()
                .filter(dish -> dish.getId().equals(dishIdForCancel))
                .findFirst();

        if (dishForDeleteOpt.isPresent()) {
            Dish dishForDelete = dishForDeleteOpt.get();
            BigDecimal dishCost = dishForDelete.getCost();
            basket.remove(dishForDelete);
            requestDataHolder.putSessionAttribute(BASKET_ATTRIBUTE, basket);
            BigDecimal orderCost = (BigDecimal) requestDataHolder.getSessionAttribute(ORDER_COST_ATTRIBUTE);
            BigDecimal newOrderCost = orderCost.subtract(dishCost);
            requestDataHolder.putSessionAttribute(ORDER_COST_ATTRIBUTE, newOrderCost);
        }

        String page = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_ATTRIBUTE);
        if (basket.isEmpty()) {
            page = MENU_PAGE;
        }

        return new CommandResult(page, true);
    }
}
