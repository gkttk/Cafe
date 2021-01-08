package com.github.gkttk.epam.logic.validator;


import java.time.LocalDateTime;

public class DataValidator implements Validator {
    @Override
    public boolean validate(String line){
        LocalDateTime orderDateTime = LocalDateTime.parse(line);
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.isBefore(orderDateTime);

    }
}
