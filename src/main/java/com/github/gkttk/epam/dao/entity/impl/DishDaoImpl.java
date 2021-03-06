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

public class DishDaoImpl extends AbstractDao<Dish> implements DishDao {

    private final static String TABLE_NAME = "dishes";
    private final static String NAME_COLUMN_KEY = "name";
    private final static String FIND_BY_TYPE_QUERY = "SELECT * from " + TABLE_NAME + " WHERE type = ? AND disabled = 0";
    private final static String FIND_ENABLED_QUERY = "SELECT * from " + TABLE_NAME + " WHERE disabled = 0";
    private final static String DISABLE_BY_ID_QUERY = "UPDATE " + TABLE_NAME + " SET disabled = 1 WHERE id = ?";
    private final static String GET_DISH_NAMES_BY_ORDER_ID_QUERY = "SELECT d.name FROM " + TABLE_NAME +
            " d JOIN orders_dishes od on d.id = od.dish_id WHERE od.order_id = ?";

    public DishDaoImpl(Connection connection) {
        super(connection, new DishRowMapper(), new DishFieldExtractor());
    }

    @Override
    public List<Dish> findAllByType(String dishType) throws DaoException {
        return getAllResults(FIND_BY_TYPE_QUERY, dishType);
    }

    @Override
    public List<Dish> findAllEnabled() throws DaoException {
        return getAllResults(FIND_ENABLED_QUERY);
    }

    @Override
    public void disable(long dishId) throws DaoException {
        try (PreparedStatement prepareStatement = createPrepareStatement(DISABLE_BY_ID_QUERY, dishId)) {
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't disable(dishId) with dishId: %d", dishId));
        }
    }

    @Override
    public List<String> findDishNamesByOrderId(long orderId) throws DaoException {
        try (PreparedStatement prepareStatement = createPrepareStatement(GET_DISH_NAMES_BY_ORDER_ID_QUERY, orderId)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            return getDishNames(resultSet);
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't findDishNamesByOrderId(orderId) with orderId: %d", orderId));
        }
    }

    private List<String> getDishNames(ResultSet resultSet) throws SQLException {
        List<String> dishNames = new ArrayList<>();
        while (resultSet.next()) {
            String dishName = resultSet.getString(NAME_COLUMN_KEY);
            dishNames.add(dishName);
        }
        return dishNames;
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

}
