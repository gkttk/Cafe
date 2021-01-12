package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DishNameValidatorTest {

    private final Validator dishNameValidator = new DishNameValidator();

    @Test
    public void testValidateShouldReturnTrueIfGivenStringMatchesPattern() throws ServiceException {
        //given
        String testLine = "Dish with correct name";
        //when
        boolean result = dishNameValidator.validate(testLine);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringDoesntMatchPattern() throws ServiceException {
        //given
        String testLine = "Too long name of dish 321312312";
        //when
        boolean result = dishNameValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsNull() throws ServiceException {
        //given
        String testLine = null;
        //when
        boolean result = dishNameValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }

}
