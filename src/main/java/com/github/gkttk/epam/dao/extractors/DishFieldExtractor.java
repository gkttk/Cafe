package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class DishFieldExtractor implements FieldExtractor<Dish> {
    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String TYPE_KEY = "type";
    private final static String COST_KEY = "cost";
    private final static String IMG_KEY = "img";

    @Override
    public Map<String, Object> extractFields(Dish dish) {
        Map<String, Object> result = new LinkedHashMap<>();

        Long id = dish.getId();
        result.put(ID_KEY, id);

        String name = dish.getName();
        result.put(NAME_KEY, name);

        DishType type = dish.getType();
        String typeName = type.toString();
        result.put(TYPE_KEY, typeName);

        BigDecimal cost = dish.getCost();
        result.put(COST_KEY, cost);

        String imgBase64 = dish.getImgBase64();
        byte[] img = Base64.getDecoder().decode(imgBase64);
        result.put(IMG_KEY, img);

        return result;

    }
}
