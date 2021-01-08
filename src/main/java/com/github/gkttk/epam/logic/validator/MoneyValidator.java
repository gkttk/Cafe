package com.github.gkttk.epam.logic.validator;


public class MoneyValidator implements Validator {
    @Override
    public boolean validate(String line) {
        try {
            Double.parseDouble(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
