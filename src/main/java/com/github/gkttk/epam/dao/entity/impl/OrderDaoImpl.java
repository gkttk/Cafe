package com.github.gkttk.epam.dao.entity.impl;

import com.github.gkttk.epam.dao.entity.AbstractDao;
import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.dao.extractors.OrderFieldExtractor;
import com.github.gkttk.epam.dao.mappers.OrderRowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private final static String TABLE_NAME = "orders";

    private final static String SAVE_ORDER_PRODUCT_QUERY = "INSERT INTO orders_dishes values (?, ?)";
    private final static String GET_ALL_BY_USER_ID_QUERY = "SELECT * FROM orders WHERE user_id = ?";

    private final static String FIND_ALL_ACTIVE_WITH_EXPIRED_DATE_QUERY = "SELECT * from orders where date < NOW() AND status = 'ACTIVE'";
    private final static String FIND_ALL_ACTIVE_BY_USER_ID = "SELECT * FROM orders WHERE status = 'ACTIVE' AND user_id = ?";
    private final static String FIND_ALL_NOT_ACTIVE_BY_USER_ID = "SELECT * FROM orders WHERE status NOT LIKE 'ACTIVE' AND user_id = ?";

    public OrderDaoImpl(Connection connection) {
        super(connection, new OrderRowMapper(), new OrderFieldExtractor());
    }

    @Override
    public void saveOrderDish(long orderId, long dishId) throws DaoException {
        try (PreparedStatement preparedStatement = createPrepareStatement(SAVE_ORDER_PRODUCT_QUERY, orderId, dishId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't saveOrderDish() with orderId: %d, dishId: %d, ", orderId, dishId), e);
        }
    }

    @Override
    public List<Order> findAllByUserId(Long userId) throws DaoException {
        return getAllResults(GET_ALL_BY_USER_ID_QUERY, userId);
    }

    @Override
    public List<Order> findAllActiveWithExpiredDate() throws DaoException {
        return getAllResults(FIND_ALL_ACTIVE_WITH_EXPIRED_DATE_QUERY);
    }

    @Override
    public List<Order> findAllActiveByUserId(long userId) throws DaoException {
        return getAllResults(FIND_ALL_ACTIVE_BY_USER_ID, userId);
    }

    @Override
    public List<Order> findAllNotActiveByUserId(long userId) throws DaoException {
        return getAllResults(FIND_ALL_NOT_ACTIVE_BY_USER_ID, userId);
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


}
