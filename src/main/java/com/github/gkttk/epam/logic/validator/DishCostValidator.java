package com.github.gkttk.epam.logic.validator;


public class DishCostValidator implements Validator {

    private final static double MIN_VALUE = 0.1;

    @Override
    public boolean validate(String line){
        try {
            Double value = Double.parseDouble(line);
            return value.compareTo(MIN_VALUE) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
