package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.DishDao;
import com.github.gkttk.epam.dao.extractors.DishFieldExtractor;
import com.github.gkttk.epam.dao.mappers.DishRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Dish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DishDaoImpl extends AbstractDao<Dish> implements DishDao {

    private final static String TABLE_NAME = "dishes";
    private final static String FIND_ALL_BY_IDS = "SELECT * FROM dishes WHERE id IN ";
    private final static String FIND_ALL_BY_ORDER_ID = "SELECT d.name, d.type from dishes d" +
            " JOIN orders_dishes od on d.id = ?";
    private final static String FIND_BY_TYPE = "SELECT * from " + TABLE_NAME + " WHERE type = ?";


    public DishDaoImpl(Connection connection) {
        super(connection, new DishRowMapper(), new DishFieldExtractor());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    @Override
    public List<Dish> findDishesByOrderId(Long orderId) throws DaoException {
        return getAllResults(FIND_ALL_BY_ORDER_ID, orderId);


    }

    @Override
    public List<Dish> findDishesByType(String dishType) throws DaoException {
        return getAllResults(FIND_BY_TYPE, dishType);
    }




    @Override
    public List<Dish> findDishesByIds(List<Long> ids) throws DaoException {
        int size = ids.size();
        StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
        for (int i = 0; i < size; i++) {
            stringJoiner.add("?");
        }
        String query = FIND_ALL_BY_IDS + stringJoiner.toString();//todo different private method

        List<Dish> dishes = new ArrayList<>();
        Object[] arrayIds = ids.toArray();
        try (PreparedStatement preparedStatement = createPrepareStatement(query, arrayIds)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Dish dish = getRowMapper().map(resultSet);
                dishes.add(dish);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't get Dishes with ids: %s", ids.toString()), e);
        }


        return dishes;
    }


}
