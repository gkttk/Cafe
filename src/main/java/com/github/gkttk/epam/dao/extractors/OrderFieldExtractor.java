package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderFieldExtractor implements FieldExtractor<Order> {

    private final static String ID_KEY = "id";
    private final static String COST_KEY = "cost";
    private final static String DATE_KEY = "date";
    private final static String STATUS_KEY = "status";
    private final static String USER_ID_KEY = "user_id";

    @Override
    public Map<String, Object> extractFields(Order order) {
        Map<String, Object> result = new LinkedHashMap<>();

        Long id = order.getId();
        result.put(ID_KEY, id);

        BigDecimal cost = order.getCost();
        result.put(COST_KEY, cost);

        LocalDateTime date = order.getDate();
        result.put(DATE_KEY, date);

        OrderStatus orderStatus = order.getStatus();
        String orderStatusName = orderStatus.name();
        result.put(STATUS_KEY, orderStatusName);

        Long userId = order.getUserId();
        result.put(USER_ID_KEY, userId);

        return result;

    }
}
