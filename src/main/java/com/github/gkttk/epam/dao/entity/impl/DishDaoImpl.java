package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.DishDao;
import com.github.gkttk.epam.dao.extractors.DishFieldExtractor;
import com.github.gkttk.epam.dao.mappers.DishRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Dish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DishDaoImpl extends AbstractDao<Dish> implements DishDao {

    private final static String TABLE_NAME = "dishes";
    private final static String FIND_BY_TYPE = "SELECT * from " + TABLE_NAME + " WHERE type = ? AND disabled = 0";
    private final static String FIND_ENABLED = "SELECT * from " + TABLE_NAME + " WHERE disabled = 0";
    private final static String DISABLE_BY_ID = "UPDATE " + TABLE_NAME + " SET disabled = 1 WHERE id = ?";

    public DishDaoImpl(Connection connection) {
        super(connection, new DishRowMapper(), new DishFieldExtractor());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    @Override
    public List<Dish> findDishesByType(String dishType) throws DaoException {
        return getAllResults(FIND_BY_TYPE, dishType);
    }

    @Override
    public void disable(long dishId) throws DaoException {
        try (PreparedStatement prepareStatement = createPrepareStatement(DISABLE_BY_ID, dishId)){
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't disable(dishId) with dishId: %d",dishId));
        }
    }

    @Override
    public List<Dish> findAllEnabled() throws DaoException {
        return getAllResults(FIND_ENABLED);
    }

}
