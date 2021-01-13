package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class DishRowMapperTest {

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String TYPE_KEY = "type";
    private final static String COST_KEY = "cost";
    private final static String IMG_BASE64_KEY = "img_base64";

    private final RowMapper<Dish> rowMapper = new DishRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long id = 1L;
        String name = "nameTest";
        DishTypes type = DishTypes.SALAD;

        BigDecimal cost = new BigDecimal(10);
        String imgBase64 = "imgBase64Test";

        Dish expectedEntity = new Dish(id, name, type, cost, imgBase64);

        when(resultSetMock.getLong(ID_KEY)).thenReturn(id);
        when(resultSetMock.getString(NAME_KEY)).thenReturn(name);
        when(resultSetMock.getString(TYPE_KEY)).thenReturn(type.name());
        when(resultSetMock.getBigDecimal(COST_KEY)).thenReturn(cost);
        when(resultSetMock.getString(IMG_BASE64_KEY)).thenReturn(imgBase64);

        //when
        Dish result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}