package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MakeOrderCommand implements Command {

    private final static String MAKE_ORDER_PAGE = "/WEB-INF/view/make_order.jsp";
    private final static String CURRENT_PAGE_PARAMETER = "currentPage";

    private final DishService dishService;

    public MakeOrderCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();

        String[] dish_ids = request.getParameterValues("orderDishes");
        List<Long> ids = Arrays.stream(dish_ids).map(Long::parseLong).collect(Collectors.toList());
        List<Dish> dishesByIds = dishService.getDishesByIds(ids);

        Optional<BigDecimal> sumOptional = dishesByIds.stream().map(Dish::getCost).reduce(BigDecimal::add);
        BigDecimal orderSum = new BigDecimal(0);
        if (sumOptional.isPresent()) {
            orderSum = sumOptional.get();
        }

        session.setAttribute("orderDishes", dishesByIds);
        session.setAttribute("orderCost", orderSum);

        session.setAttribute(CURRENT_PAGE_PARAMETER, MAKE_ORDER_PAGE);

        return new CommandResult(MAKE_ORDER_PAGE, true);
    }


}
