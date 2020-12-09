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

    private final static String MAKE_ORDER_PAGE = "/WEB-INF/view/make_order.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";
    private final static String ERROR_MESSAGE = "You chose nothing!";//todo

    private final DishService dishService;

    public MakeOrderCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(RequestDataHolder requestDataHolder) throws ServiceException {


        String[] dish_ids = requestDataHolder.getRequestParameters("orderDishes");
        if(dish_ids == null){
            requestDataHolder.putRequestAttribute("errorMessage", ERROR_MESSAGE);
            String currentPage = (String) requestDataHolder.getSessionAttribute(CURRENT_PAGE_PARAMETER);

            return new CommandResult(currentPage, false);
        }

        List<Long> ids = Arrays.stream(dish_ids).map(Long::parseLong).collect(Collectors.toList());
        List<Dish> dishesByIds = dishService.getDishesByIds(ids);

        Optional<BigDecimal> sumOptional = dishesByIds.stream().map(Dish::getCost).reduce(BigDecimal::add);
        BigDecimal orderSum = new BigDecimal(0);
        if (sumOptional.isPresent()) {
            orderSum = sumOptional.get();
        }

        requestDataHolder.putSessionAttribute("orderDishes", dishesByIds);
        requestDataHolder.putSessionAttribute("orderCost", orderSum);

        requestDataHolder.putSessionAttribute(CURRENT_PAGE_PARAMETER, MAKE_ORDER_PAGE);

        return new CommandResult(MAKE_ORDER_PAGE, true);
    }


}
