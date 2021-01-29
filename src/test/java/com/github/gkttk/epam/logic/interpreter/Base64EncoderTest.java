package com.github.gkttk.epam.logic.interpreter;

import com.github.gkttk.epam.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static org.mockito.Mockito.when;

public class Base64EncoderTest {

    private Part partMock;

    @BeforeEach
    void init() {
        this.partMock = Mockito.mock(Part.class);
    }


    @Test
    public void testInterpretShouldReturnStringWhenGetNotEmptyPart() throws ServiceException, IOException {
        //given
        byte[] bytes = new byte[100];
        new Random().nextBytes(bytes);
        InputStream filledInputStream = new ByteArrayInputStream(bytes);

        when(partMock.getInputStream()).thenReturn(filledInputStream);
        //when
        String result = Base64Encoder.encode(partMock);
        //then
        Assertions.assertNotNull(result);

    }

    @Test
    public void testInterpretShouldReturnNullWhenGetEmptyPart() throws ServiceException, IOException {
        //given
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);

        when(partMock.getInputStream()).thenReturn(emptyInputStream);
        //when
        String result = Base64Encoder.encode(partMock);
        //then
        Assertions.assertNull(result);
    }

    @Test
    public void testInterpretShouldThrowExceptionWhenCantGetStream() throws IOException {
        //given
        when(partMock.getInputStream()).thenThrow(new IOException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> Base64Encoder.encode(partMock));
    }


}
