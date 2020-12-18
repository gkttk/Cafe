package com.github.gkttk.epam.logic.validator.factory;

import com.github.gkttk.epam.logic.validator.UserLoginValidator;
import com.github.gkttk.epam.logic.validator.UserPasswordValidator;
import com.github.gkttk.epam.logic.validator.Validator;

public class ValidatorFactory {


    public static Validator getUserLoginValidator(){
        return new UserLoginValidator();
    }

    public static Validator getUserPasswordValidator(){
        return new UserPasswordValidator();
    }
}
