package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserPasswordValidatorTest {

    private final Validator userPasswordValidator = new UserPasswordValidator();

    @Test
    public void testValidateShouldReturnTrueIfGivenStringMatchesPattern() throws ServiceException {
        //given
        String correctPassword = "123As";
        //when
        boolean result = userPasswordValidator.validate(correctPassword);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringDoesntMatchPattern() throws ServiceException {
        //given
        String incorrectPassword = "pass123";
        //when
        boolean result = userPasswordValidator.validate(incorrectPassword);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsNull() throws ServiceException {
        //given
        String nullPassword = null;
        //when
        boolean result = userPasswordValidator.validate(nullPassword);
        //then
        Assertions.assertFalse(result);
    }

}
