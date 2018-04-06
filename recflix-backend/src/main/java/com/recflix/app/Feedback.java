package com.recflix.app;

import java.time.ZonedDateTime;

public class Feedback {

    private final String id;
    private final Integer rating;
    private final String type;
    private final ZonedDateTime createdAt;
    private final String userId;
    private final String movieId;

    public Feedback(Integer rating, String type, ZonedDateTime createdAt, String userId, String movieId) {
        this(null, rating, type, createdAt, userId, movieId);
    }

    public Feedback(String id, Integer rating, String type, ZonedDateTime createdAt, String userId, String movieId) {
        this.id = id;
        this.rating = rating;
        this.type = type;
        this.userId = userId;
        this.movieId = movieId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public Integer getRating() {
        return rating;
    }
}