package com.github.gkttk.epam.logic.interpreter;

import com.github.gkttk.epam.exceptions.ServiceException;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * Class interprets given InputStream to Base64String. Return null if InputStream has 0 available bytes for reading.
 */
public class Base64Encoder {

    public static String encode(Part part) throws ServiceException {
        try {
            InputStream inputStream = part.getInputStream();
            int availableBytes = inputStream.available();
            if (availableBytes == 0) {
                return null;
            }
            byte[] buffer = new byte[availableBytes];
            inputStream.read(buffer);
            return Base64.getEncoder().encodeToString(buffer);
        } catch (IOException e) {
            throw new ServiceException("Base64Interpreter can't interpret given Input Stream into String", e);
        }

    }



}
