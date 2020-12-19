package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        BigDecimal cost = resultSet.getBigDecimal("cost");
        Timestamp dateStamp = resultSet.getTimestamp("date");
        LocalDateTime date = dateStamp.toLocalDateTime();
        String statusStr = resultSet.getString("status");
        OrderStatus orderStatus = OrderStatus.valueOf(statusStr);
        long user_id = resultSet.getLong("user_id");

        return new Order(id, cost, date, orderStatus, user_id);
    }
}
