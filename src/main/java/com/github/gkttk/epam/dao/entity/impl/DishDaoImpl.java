package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.DishDao;
import com.github.gkttk.epam.dao.extractors.DishFieldExtractor;
import com.github.gkttk.epam.dao.mappers.DishRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Dish;

import java.sql.Connection;
import java.util.List;

public class DishDaoImpl extends AbstractDao<Dish> implements DishDao {

    private final static String TABLE_NAME = "dishes";
    private final static String FIND_BY_TYPE = "SELECT * from " + TABLE_NAME + " WHERE type = ?";

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

}
