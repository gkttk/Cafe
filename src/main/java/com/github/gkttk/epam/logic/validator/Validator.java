package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.exceptions.ServiceException;

public interface Validator {

    boolean validate(String line) throws ServiceException;

}
