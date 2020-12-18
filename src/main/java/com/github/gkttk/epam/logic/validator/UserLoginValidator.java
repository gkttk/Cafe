package com.github.gkttk.epam.logic.validator;

public class UserLoginValidator implements Validator {

    private final static String LOGIN_REGEX = "\\D([a-zA-Z1-9]{3,9})";



    @Override
    public boolean validate(String line) {
        return line.matches(LOGIN_REGEX);
    }
}
