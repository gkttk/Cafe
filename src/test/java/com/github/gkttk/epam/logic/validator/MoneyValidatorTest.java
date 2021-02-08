package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyValidatorTest {

    private final Validator moneyValidator = new MoneyValidator();

    @Test
    public void testValidateShouldReturnTrueIfGivenStringIsDoubleTypeAndInCorrectRange() throws ServiceException {
        //given
        String testLine = "250.2";
        //when
        boolean result = moneyValidator.validate(testLine);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnTrueIfGivenStringIsDoubleTypeAndNotInCorrectRange() throws ServiceException {
        //given
        String testLine = "500.1";
        //when
        boolean result = moneyValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }


    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsNotDoubleType() throws ServiceException {
        //given
        String testLine = "Hello, world!";
        //when
        boolean result = moneyValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsNull() throws ServiceException {
        //given
        String testLine = "null";
        //when
        boolean result = moneyValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }


}
