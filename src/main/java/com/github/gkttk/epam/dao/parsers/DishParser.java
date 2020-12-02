package com.github.gkttk.epam.dao.parsers;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DishParser implements EntityParser<Dish> {
    @Override
    public List<Object> parse(Dish dish) {

        Long id = dish.getId();
        String name = dish.getName();
        DishTypes type = dish.getType();
        String typeName = type.toString();
        BigDecimal cost = dish.getCost();

        return Arrays.asList(id, name, typeName, cost);

    }
}
