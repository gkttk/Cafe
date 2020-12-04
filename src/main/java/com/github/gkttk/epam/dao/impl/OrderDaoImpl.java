package com.github.gkttk.epam.dao.impl;

import com.github.gkttk.epam.dao.AbstractDao;
import com.github.gkttk.epam.dao.OrderDao;
import com.github.gkttk.epam.dao.extractors.OrderFieldExtractor;
import com.github.gkttk.epam.dao.mappers.OrderRowMapper;
import com.github.gkttk.epam.model.entities.Order;

import java.sql.Connection;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private final static String TABLE_NAME = "order";

    public OrderDaoImpl(Connection connection) {
        super(connection, new OrderRowMapper(), new OrderFieldExtractor());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
