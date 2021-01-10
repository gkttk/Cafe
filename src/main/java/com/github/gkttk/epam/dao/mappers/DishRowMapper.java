package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DishRowMapper implements RowMapper<Dish> {
    @Override
    public Dish map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String typeName = resultSet.getString("type");
        DishTypes type = DishTypes.valueOf(typeName);
        BigDecimal cost = resultSet.getBigDecimal("cost");
        String imgBase64 = resultSet.getString("img_base64");

        return new Dish(id, name, type, cost, imgBase64);

    }
}
