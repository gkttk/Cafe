package com.github.gkttk.epam.model.enums;

public enum UserStatus {
    ACTIVE(false),
    BLOCKED(true);

    private boolean isBlocked;

    UserStatus(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
}
