package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DishCostValidatorTest {

    private final Validator dishCostValidator = new DishCostValidator();

    @Test
    public void testValidateShouldReturnTrueIfGivenStringIsDoubleTypeAndInCorrectRange() throws ServiceException {
        //given
        String testLine = "15";
        //when
        boolean result = dishCostValidator.validate(testLine);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsDoubleTypeAndNotInCorrectRange() throws ServiceException {
        //given
        String testLine = "25.1";
        //when
        boolean result = dishCostValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsNotDouble() throws ServiceException {
        //given
        String testLine = "Hello, world!";
        //when
        boolean result = dishCostValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsEqualZero() throws ServiceException {
        //given
        String testLine = "0";
        //when
        boolean result = dishCostValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsLessThanZero() throws ServiceException {
        //given
        String testLine = "-1.5";
        //when
        boolean result = dishCostValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }


}
