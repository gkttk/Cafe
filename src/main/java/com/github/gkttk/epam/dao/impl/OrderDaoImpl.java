package com.github.gkttk.epam.dao.impl;

import com.github.gkttk.epam.dao.AbstractDao;
import com.github.gkttk.epam.dao.OrderDao;
import com.github.gkttk.epam.dao.extractors.OrderFieldExtractor;
import com.github.gkttk.epam.dao.mappers.OrderRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private final static String TABLE_NAME = "orders";

    private final static String SAVE_ORDER_PRODUCT_QUERY = "INSERT INTO orders_dishes values (?, ?)"; //todo
    private final static String GET_ALL_BY_USER_ID_QUERY = "SELECT * FROM orders WHERE user_id = ?";


    public OrderDaoImpl(Connection connection) {
        super(connection, new OrderRowMapper(), new OrderFieldExtractor());
    }

    @Override
    public void saveOrderDish(Long order_id, Long dish_id) throws DaoException {
        try(PreparedStatement preparedStatement = createPrepareStatement(SAVE_ORDER_PRODUCT_QUERY, order_id, dish_id)) {
           preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't saveOrderDish() with order_id: %d, dish_id: %d, ",order_id, dish_id), e);
        }
    }

    @Override
    public List<Order> findAllByUserId(Long userId) throws DaoException {
        return getAllResults(GET_ALL_BY_USER_ID_QUERY, userId);
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }



}
