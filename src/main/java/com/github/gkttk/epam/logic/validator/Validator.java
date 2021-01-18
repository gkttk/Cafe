package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;

/**
 * Implementations of current interface validates given lines.
 */
public interface Validator {

    boolean validate(String line) throws ServiceException;

}
