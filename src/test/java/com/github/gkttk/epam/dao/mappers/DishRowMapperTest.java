package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import static org.mockito.Mockito.when;

public class DishRowMapperTest {

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String TYPE_KEY = "type";
    private final static String COST_KEY = "cost";
    private final static String IMG_KEY = "img";

    private final RowMapper<Dish> rowMapper = new DishRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long id = 1L;
        String name = "nameTest";
        DishType type = DishType.SALAD;

        BigDecimal cost = new BigDecimal(10);
        String imgBase64 = "dGVzdEltZ0Jhc2U2NA==";
        byte[] img = Base64.getDecoder().decode(imgBase64);

        Blob blobMock = Mockito.mock(Blob.class);
        Dish expectedEntity = new Dish(id, name, type, cost, imgBase64);

        when(resultSetMock.getLong(ID_KEY)).thenReturn(id);
        when(resultSetMock.getString(NAME_KEY)).thenReturn(name);
        when(resultSetMock.getString(TYPE_KEY)).thenReturn(type.name());
        when(resultSetMock.getBigDecimal(COST_KEY)).thenReturn(cost);
        when(resultSetMock.getBlob(IMG_KEY)).thenReturn(blobMock);
        when(blobMock.length()).thenReturn((long) img.length);
        when(blobMock.getBytes(1, img.length)).thenReturn(img);
        //when
        Dish result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}