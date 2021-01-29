package com.github.gkttk.epam.model;

import java.util.Objects;

/**
 * Result of executing any command. Contains url for redirect/forward.
 */
public class CommandResult {

    private final String url;
    private final boolean isRedirect;


    public CommandResult(String url, boolean isRedirect) {
        this.url = url;
        this.isRedirect = isRedirect;
    }


    public String getUrl() {
        return url;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandResult that = (CommandResult) o;
        return isRedirect == that.isRedirect &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, isRedirect);
    }
}
