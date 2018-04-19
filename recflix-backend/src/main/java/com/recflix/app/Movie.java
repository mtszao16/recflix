package com.recflix.app;

/**
 * Represents a movie of the system
 */
public class Movie {

    private final String id;
    private final String name;
    private final String url;
    private final Integer totalDuration;

    public Movie(String name, String url, Integer totalDuration) {
        this(null, name, url, totalDuration);
    }

    public Movie(String id, String name, String url, Integer totalDuration) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.totalDuration = totalDuration;
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

    public Integer getTotalDuration() {
        return totalDuration;
    }
}
