package com.github.gkttk.epam.logic.validator.factory;

import com.github.gkttk.epam.logic.validator.*;

public class ValidatorFactory {


    public static Validator getUserLoginValidator() {
        return new UserLoginValidator();
    }

    public static Validator getUserPasswordValidator() {
        return new UserPasswordValidator();
    }

    public static Validator getDataValidator() {
        return new DataValidator();

    }

    public static Validator getMoneyValidator() {
        return new MoneyValidator();
    }

    public static Validator getCommentValidator() {
        return new CommentValidator();
    }
}
