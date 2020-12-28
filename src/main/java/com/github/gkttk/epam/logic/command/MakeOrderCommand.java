package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.holder.RequestDataHolder;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MakeOrderCommand implements Command {

    private final static String BASKET_ATTR = "basket";
    private final static String ORDER_COST_ATTR = "orderCost";
    private final static String CURRENT_PAGE_PARAM = "currentPage";
    private final static String ERROR_MESSAGE_ATTR = "errorMessage";
    private final static String ERROR_MESSAGE = "error.message.empty.basket";
    private final static String MAKE_ORDER_PAGE = "/WEB-INF/view/make_order.jsp";

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) {

        List<Dish> basket = (List<Dish>) requestDataHolder.getSessionAttribute(BASKET_ATTR);

        if (basket == null || basket.isEmpty()) {
            requestDataHolder.putRequestAttribute(ERROR_MESSAGE_ATTR, ERROR_MESSAGE);
            String forwardPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAM);
            return new CommandResult(forwardPage, false);
        }

        Optional<BigDecimal> orderCost = getOrderCost(basket);
        orderCost.ifPresent(cost -> requestDataHolder.putSessionAttribute(ORDER_COST_ATTR, cost));

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAM, MAKE_ORDER_PAGE);

        return new CommandResult(MAKE_ORDER_PAGE, true);
    }

    private Optional<BigDecimal> getOrderCost(List<Dish> basket) {
        return basket.stream()
                .map(Dish::getCost)
                .reduce(BigDecimal::add);
    }


}
