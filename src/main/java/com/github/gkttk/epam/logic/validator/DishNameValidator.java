package com.github.gkttk.epam.logic.validator;

public class DishNameValidator implements Validator {
    private final static String DISH_NAME_REGEX = "(?=\\D)(?=\\S)[\\S\\s]{3,25}";


    @Override
    public boolean validate(String dishName) {
        return dishName != null && dishName.matches(DISH_NAME_REGEX);
    }


}
