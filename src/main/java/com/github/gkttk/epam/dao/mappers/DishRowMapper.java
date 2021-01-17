package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DishRowMapper implements RowMapper<Dish> {

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String TYPE_KEY = "type";
    private final static String COST_KEY = "cost";
    private final static String IMG_BASE64_KEY = "img_base64";

    @Override
    public Dish map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_KEY);
        String name = resultSet.getString(NAME_KEY);
        String typeName = resultSet.getString(TYPE_KEY);
        DishType type = DishType.valueOf(typeName);
        BigDecimal cost = resultSet.getBigDecimal(COST_KEY);
        String imgBase64 = resultSet.getString(IMG_BASE64_KEY);

        return new Dish(id, name, type, cost, imgBase64);

    }
}
