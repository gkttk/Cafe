package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.enums.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderFieldExtractorTest {

    private final static String ID_KEY = "id";
    private final static String COST_KEY = "cost";
    private final static String DATE_KEY = "date";
    private final static String STATUS_KEY = "status";
    private final static String USER_ID_KEY = "user_id";

    private final FieldExtractor<Order> fieldExtractor = new OrderFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long id = 1L;
        BigDecimal cost = BigDecimal.ONE;
        LocalDateTime dateTime = LocalDateTime.MIN;
        OrderStatus status = OrderStatus.ACTIVE;
        long userId = 2L;

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(ID_KEY, id);
        expectedMap.put(COST_KEY, cost);
        expectedMap.put(DATE_KEY, dateTime);
        expectedMap.put(STATUS_KEY, status.name());
        expectedMap.put(USER_ID_KEY, userId);
        Order order = new Order(id, cost, dateTime, status, userId);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(order);
        //then
        Assertions.assertEquals(expectedMap, result);

    }
}