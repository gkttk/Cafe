package com.github.gkttk.epam.logic.interpreter;

import com.github.gkttk.epam.exceptions.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Base64Interpreter {

    public static String interpret(InputStream file) throws ServiceException {
        try {
            int availableBytes = file.available();
            if (availableBytes == 0) {
                return null;
            }
            byte[] buffer = new byte[availableBytes];
            file.read(buffer);
            return Base64.getEncoder().encodeToString(buffer);
        } catch (IOException e) {
            throw new ServiceException("Base64Interpreter can't interpret given Input Stream into String", e);
        }

    }
}
