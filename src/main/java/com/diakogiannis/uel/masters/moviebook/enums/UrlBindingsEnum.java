package com.diakogiannis.uel.masters.moviebook.enums;


public enum UrlBindingsEnum {
    INDEX("index"),
    MOVIES_HOME_URI("movies/list"),
    MOVIES_HOME_TEMPLATE("movies/home"),
    MOVIES_CREATE_TEMPLATE("movies/create"),
    USER_REGISTER_TEMPLATE("user/register"),
    USER_REGISTER_URI("/register");

    private String url;

    UrlBindingsEnum(String url) {
        this.url = url;
    }

    public String getValue() {
        return this.url;
    }
}
