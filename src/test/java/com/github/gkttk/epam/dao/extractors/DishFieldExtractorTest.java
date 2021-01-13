package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class DishFieldExtractorTest {

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String TYPE_KEY = "type";
    private final static String COST_KEY = "cost";
    private final static String IMG_BASE64_KEY = "img_base64";

    private final FieldExtractor<Dish> fieldExtractor = new DishFieldExtractor();

    @Test
    public void testExtractFieldsShouldReturnMapWithCommentValues() {
        //given
        long id = 1L;
        String name = "nameTest";
        DishTypes type = DishTypes.SALAD;
        BigDecimal cost = new BigDecimal(10);
        String imgBase64 = "imgBase64Test";

        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put(ID_KEY, id);
        expectedMap.put(NAME_KEY, name);
        expectedMap.put(TYPE_KEY, type.name());
        expectedMap.put(COST_KEY, cost);
        expectedMap.put(IMG_BASE64_KEY, imgBase64);
        Dish dish = new Dish(id, name, type, cost, imgBase64);
        //when
        Map<String, Object> result = fieldExtractor.extractFields(dish);
        //then
        Assertions.assertEquals(expectedMap, result);

    }
}