package com.github.gkttk.epam.model.enums;

public enum CurrentPages {
    INDEX("index.jsp"),
    MENU("/WEB-INF/view/user_menu.jsp");

    private String reference;

    CurrentPages(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
