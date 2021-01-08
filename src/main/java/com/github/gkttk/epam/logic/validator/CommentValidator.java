package com.github.gkttk.epam.logic.validator;


public class CommentValidator implements Validator {
    @Override
    public boolean validate(String line) {
        return line.length() <= 250;
    }
}
