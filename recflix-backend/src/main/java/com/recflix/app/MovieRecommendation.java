package com.recflix.app;

/**
 * Represents a watched movie of the system
 */
public class MovieRecommendation extends Movie {

    private final String userId;
    private final Double rating;

    public MovieRecommendation(String userId, Double rating, String name, String url, Integer totalDuration) {
        this(userId, rating, null, name, url, totalDuration);
    }

    public MovieRecommendation(String userId, Double rating, String movieId, String name, String url,
            Integer totalDuration) {
        super(movieId, name, url, totalDuration);
        this.userId = userId;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public Double getRating() {
        return rating;
    }

}
