package com.github.gkttk.epam.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum UserRole {
    ADMIN("REMOVE_DISH","ADD_DISH", "CHANGE_AVATAR", "ADD_MONEY", "SORT_COMMENTS", "UPDATE_COMMENT", "DELETE_COMMENT",
            "CHANGE_POINTS", "SORT_USERS", "USERS", "CHANGE_STATUS", "LOGOUT", "MENU", "COMMENTS", "RATE_COMMENT",
            "SORT_DISHES", "DISH_COMMENTS", "ADD_COMMENT"),

    USER("CHANGE_AVATAR",
            "ADD_MONEY", "ORDER_HISTORY", "SORT_COMMENTS", "UPDATE_COMMENT", "DELETE_COMMENT", "LOGOUT", "FORM_ORDER",
            "MENU", "MY_ORDERS", "COMMENTS", "SAVE_ORDER", "CANCEL_DISH", "RATE_COMMENT", "SORT_DISHES", "TO_BASKET",
            "DISH_COMMENTS", "ADD_COMMENT", "TAKE_ORDER", "CANCEL_ORDER"
    ),
    GUEST("LOGIN", "REGISTRATION_PAGE", "REGISTRATION");

    private final List<String> availableCommandNames;

    UserRole(String... extraCommandNames) {
        this.availableCommandNames = new ArrayList<>();
        this.availableCommandNames.addAll(Arrays.asList("LOCALE", "HOME"));
        if (extraCommandNames.length != 0) {
            this.availableCommandNames.addAll(Arrays.asList(extraCommandNames));
        }
    }


    public List<String> getAvailableCommandNames() {
        return availableCommandNames;
    }
}
