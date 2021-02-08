package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class DishFieldExtractorTest {

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String TYPE_KEY = "type";
    private final static String COST_KEY = "cost";
    private final static String IMG_KEY = "img";

    private final FieldExtractor<Dish> fieldExtractor = new DishFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long id = 1L;
        String name = "nameTest";
        DishType type = DishType.SALAD;
        BigDecimal cost = new BigDecimal(10);
        String imgBase64 = "dGVzdEltZ0Jhc2U2NA==";
        byte[] img = Base64.getDecoder().decode(imgBase64);

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(ID_KEY, id);
        expectedMap.put(NAME_KEY, name);
        expectedMap.put(TYPE_KEY, type.name());
        expectedMap.put(COST_KEY, cost);
        expectedMap.put(IMG_KEY, img);
        Dish dish = new Dish(id, name, type, cost, imgBase64);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(dish);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.get(ID_KEY), id),
                () -> Assertions.assertEquals(result.get(NAME_KEY), name),
                () -> Assertions.assertEquals(result.get(TYPE_KEY), type.name()),
                () -> Assertions.assertEquals(result.get(COST_KEY), cost),
                () -> Assertions.assertArrayEquals((byte[]) result.get(IMG_KEY), img)
        );
    }
}