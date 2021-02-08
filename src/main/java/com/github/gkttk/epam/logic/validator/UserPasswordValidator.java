package com.github.gkttk.epam.logic.validator;

public class UserPasswordValidator implements Validator {

    private final static String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,10}";


    @Override
    public boolean validate(String line) {
        return line != null && line.matches(PASSWORD_REGEX);

    }
}
