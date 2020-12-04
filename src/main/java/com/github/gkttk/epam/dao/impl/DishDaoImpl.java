package com.github.gkttk.epam.dao.impl;

import com.github.gkttk.epam.dao.AbstractDao;
import com.github.gkttk.epam.dao.DishDao;
import com.github.gkttk.epam.dao.extractors.DishFieldExtractor;
import com.github.gkttk.epam.dao.mappers.DishRowMapper;
import com.github.gkttk.epam.model.entities.Dish;

import java.sql.Connection;

public class DishDaoImpl extends AbstractDao<Dish> implements DishDao {

    private final static String TABLE_NAME = "dish";

    public DishDaoImpl(Connection connection) {
        super(connection, new DishRowMapper(), new DishFieldExtractor());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
