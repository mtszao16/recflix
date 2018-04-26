package com.recflix.app;

/**
 * Represents a watched movie of the system
 */
public class WatchedMovie extends Movie {

    private final Integer watchedCount;
    private final Integer watchedDuration;

    public WatchedMovie(String name, String url, Integer totalDuration, Integer watchedCount, Integer watchedDuration) {
        this(null, name, url, totalDuration, watchedCount, watchedDuration);
    }

    public WatchedMovie(String id, String name, String url, Integer totalDuration, Integer watchedCount,
            Integer watchedDuration) {
        super(id, name, url, totalDuration);
        this.watchedCount = watchedCount;
        this.watchedDuration = watchedDuration;
    }

    public Integer getWatchedCount() {
        return watchedCount;
    }

    public Integer getWatchedDuration() {
        return watchedDuration;
    }
}
