package com.github.gkttk.epam.logic.command;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.CommandResult;
import com.github.gkttk.epam.model.entities.Dish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CancelDishCommand implements Command {

    private final static String CURRENT_PAGE_ATTRIBUTE = "currentPage";
    private final static String ORDER_DISHES_ATTRIBUTE = "orderDishes";
    private final static String DISH_ID_PARAMETER = "dishId";
    private final static String MENU_PAGE = "/WEB-INF/view/user_menu.jsp";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();

        List<Dish> orderDishes = (List<Dish>)session.getAttribute(ORDER_DISHES_ATTRIBUTE);



        String dishIdParam = request.getParameter(DISH_ID_PARAMETER);
        long dishIdForCancel = Long.parseLong(dishIdParam);

        orderDishes.removeIf(dish -> dish.getId() == dishIdForCancel);

        session.setAttribute(ORDER_DISHES_ATTRIBUTE, orderDishes);

        String page = (String)session.getAttribute(CURRENT_PAGE_ATTRIBUTE);

        if (orderDishes.isEmpty()){
            page = MENU_PAGE;
        }

        return new CommandResult(page, true);
    }
}
