package com.github.gkttk.epam.dao.parsers;

import com.github.gkttk.epam.model.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OrderParser implements EntityParser<Order> {
    @Override
    public List<Object> parse(Order order) {

        Long id = order.getId();
        BigDecimal cost = order.getCost();
        LocalDateTime time = order.getTime();
        boolean active = order.isActive();

        return Arrays.asList(id, cost, time, active);

    }
}
