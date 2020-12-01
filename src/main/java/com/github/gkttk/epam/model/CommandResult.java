package com.github.gkttk.epam.model;

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
}
