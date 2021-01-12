package com.github.gkttk.epam.logic.validator;


public class MoneyValidator implements Validator {
    private final static double MIN_VALUE = 0.0;
    private final static double MAX_VALUE = 500;

    @Override
    public boolean validate(String line) {
        try {
            Double value = Double.parseDouble(line);
            return value.compareTo(MIN_VALUE) >= 0 && value.compareTo(MAX_VALUE) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
