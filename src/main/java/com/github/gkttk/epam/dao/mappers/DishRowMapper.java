package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class DishRowMapper implements RowMapper<Dish> {

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String TYPE_KEY = "type";
    private final static String COST_KEY = "cost";
    private final static String IMG_KEY = "img";

    @Override
    public Dish map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_KEY);
        String name = resultSet.getString(NAME_KEY);
        String typeName = resultSet.getString(TYPE_KEY);
        DishType type = DishType.valueOf(typeName);
        BigDecimal cost = resultSet.getBigDecimal(COST_KEY);


        Blob imgBlob = resultSet.getBlob(IMG_KEY);
        byte[] img = imgBlob.getBytes(1, (int) imgBlob.length());
        String imgBase64 = Base64.getEncoder().encodeToString(img);

        return new Dish(id, name, type, cost, imgBase64);

    }
}
