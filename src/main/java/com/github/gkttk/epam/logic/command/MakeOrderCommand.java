package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.controller.handler.RequestDataHolder;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MakeOrderCommand implements Command {

    private final static String BASKET_ATTRIBUTE = "basket";
    private final static String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private final static String ERROR_MESSAGE = "Your basket is empty!";//todo to resourcebundle
    private final static String ORDER_COST_ATTRIBUTE = "orderCost";

    private final static String MAKE_ORDER_PAGE = "/WEB-INF/view/make_order.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";


    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder){

        List<Dish> basket = (List<Dish>)requestDataHolder.getSessionAttribute(BASKET_ATTRIBUTE);

        if(basket == null || basket.isEmpty()){
            requestDataHolder.putRequestAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE);
            String currentPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAMETER);
            return new CommandResult(currentPage, false);
        }

        Optional<BigDecimal> costSumOpt = basket.stream()
                .map(Dish::getCost)
                .reduce(BigDecimal::add);
        costSumOpt.ifPresent(sum -> requestDataHolder.putSessionAttribute(ORDER_COST_ATTRIBUTE, sum));

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, MAKE_ORDER_PAGE);

        return new CommandResult(MAKE_ORDER_PAGE, true);
    }


}
