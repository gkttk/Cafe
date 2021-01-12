package com.github.gkttk.epam.model.enums;

public enum DishTypes {
    SOUP("user.menu.add.dish.button.unknown.soup"),
    SALAD("user.menu.add.dish.button.unknown.salad"),
    BEVERAGE("user.menu.add.dish.button.unknown.beverage");

    private String bundleKey;

    DishTypes(String bundleKey) {
        this.bundleKey = bundleKey;
    }

    public String getBundleKey() {
        return bundleKey;
    }
}
