package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderRowMapper implements RowMapper<Order> {

    private final static String ID_KEY = "id";
    private final static String COST_KEY = "cost";
    private final static String DATE_KEY = "date";
    private final static String STATUS_KEY = "status";
    private final static String USER_ID_KEY = "user_id";

    @Override
    public Order map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_KEY);

        BigDecimal cost = resultSet.getBigDecimal(COST_KEY);

        Timestamp dateStamp = resultSet.getTimestamp(DATE_KEY);
        LocalDateTime date = dateStamp.toLocalDateTime();

        String statusStr = resultSet.getString(STATUS_KEY);
        OrderStatus orderStatus = OrderStatus.valueOf(statusStr);

        long user_id = resultSet.getLong(USER_ID_KEY);

        return new Order(id, cost, date, orderStatus, user_id);
    }
}
