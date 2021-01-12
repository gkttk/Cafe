package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateValidatorTest {

    private final Validator dataValidator = new DateValidator();

    @Test
    public void testValidateShouldReturnTrueIfGivenStringDateIsAfterThanCurrent() throws ServiceException {
        //given
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime givenDate = currentDate.plus(1, ChronoUnit.HOURS);
        String dateString = givenDate.toString();
        //when
        boolean result = dataValidator.validate(dateString);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringDateIsBeforeThanCurrent() throws ServiceException {
        //given
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime givenDate = currentDate.minus(1, ChronoUnit.HOURS);
        String dateString = givenDate.toString();
        //when
        boolean result = dataValidator.validate(dateString);
        //then
        Assertions.assertFalse(result);
    }


    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsEmpty() throws ServiceException {
        //given
        String dateString = "";
        //when
        boolean result = dataValidator.validate(dateString);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsNull() throws ServiceException {
        //given
        String dateString = null;
        //when
        boolean result = dataValidator.validate(dateString);
        //then
        Assertions.assertFalse(result);
    }


}
