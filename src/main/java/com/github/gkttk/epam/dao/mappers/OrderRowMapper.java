package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Order;

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
        Timestamp timestamp = resultSet.getTimestamp("time");
        LocalDateTime time = timestamp.toLocalDateTime();
        boolean active = resultSet.getBoolean("active");
        long user_id = resultSet.getLong("user_id");

        return new Order(id, cost, time, active, user_id);
    }
}
