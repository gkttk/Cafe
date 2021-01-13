package com.github.gkttk.epam.logic.interpreter;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static org.mockito.Mockito.when;

public class Base64InterpreterTest {

    @Test
    public void testInterpretShouldReturnStringWhenGetNotEmptyInputStream() throws ServiceException {
        //given
        byte[] bytes = new byte[100];
        new Random().nextBytes(bytes);
        InputStream filledInputStream = new ByteArrayInputStream(bytes);
        //when
        String result = Base64Interpreter.interpret(filledInputStream);
        //then
        Assertions.assertNotNull(result);

    }

    @Test
    public void testInterpretShouldReturnNullWhenGetEmptyInputStream() throws ServiceException {
        //given
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        //when
        String result = Base64Interpreter.interpret(emptyInputStream);
        //then
        Assertions.assertNull(result);
    }

    @Test
    public void testInterpretShouldThrowExceptionWhenCantGetStream() throws IOException {
        //given
        InputStream inputStreamMock = Mockito.mock(InputStream.class);
        when(inputStreamMock.available()).thenThrow(new IOException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> Base64Interpreter.interpret(inputStreamMock));
    }


}
