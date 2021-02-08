package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.enums.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

public class OrderRowMapperTest {

    private final static String ID_KEY = "id";
    private final static String COST_KEY = "cost";
    private final static String DATE_KEY = "date";
    private final static String STATUS_KEY = "status";
    private final static String USER_ID_KEY = "user_id";

    private final RowMapper<Order> rowMapper = new OrderRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long id = 1L;
        BigDecimal cost = BigDecimal.ONE;
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        OrderStatus status = OrderStatus.ACTIVE;
        long userId = 2L;

        Order expectedEntity = new Order(id, cost, dateTime, status, userId);

        when(resultSetMock.getLong(ID_KEY)).thenReturn(id);
        when(resultSetMock.getBigDecimal(COST_KEY)).thenReturn(cost);
        when(resultSetMock.getTimestamp(DATE_KEY)).thenReturn(timestamp);
        when(resultSetMock.getString(STATUS_KEY)).thenReturn(status.name());
        when(resultSetMock.getLong(USER_ID_KEY)).thenReturn(userId);

        //when
        Order result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}