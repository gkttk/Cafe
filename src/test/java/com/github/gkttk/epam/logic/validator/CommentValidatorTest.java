package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommentValidatorTest {

    private final Validator commentValidator = new CommentValidator();

    @Test
    public void testValidateShouldReturnTrueIfGivenStringIsShorterThan250Symbols() throws ServiceException {
        //given
        String testLine = "Hello, world!";
        //when
        boolean result = commentValidator.validate(testLine);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnTrueIfGivenStringIsEmpty() throws ServiceException {
        //given
        String testLine = "";
        //when
        boolean result = commentValidator.validate(testLine);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsLongerThan250Symbols() throws ServiceException {
        //given
        String testLine = IntStream.range(0, 251).mapToObj(number -> "a").collect(Collectors.joining());
        //when
        boolean result = commentValidator.validate(testLine);
        //then
        Assertions.assertFalse(result);
    }
}
