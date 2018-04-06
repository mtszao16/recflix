package com.recflix.app;

/**
 * Represents a movie of the system
 */
public class Movie {

    private final String id;
    private final String name;
    private final String url;

    public Movie(String name, String url) {
        this(null, name, url);
    }

    public Movie(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
