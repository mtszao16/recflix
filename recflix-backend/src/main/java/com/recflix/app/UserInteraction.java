package com.recflix.app;

import java.time.ZonedDateTime;

public class UserInteraction {

    private final ZonedDateTime interactionTime;
    private final String interactionType;
    private final String userId;
    private final String movieId;

    public UserInteraction(ZonedDateTime interactionTime, String interactionType, String userId, String movieId) {
        this.interactionTime = interactionTime;
        this.interactionType = interactionType;
        this.userId = userId;
        this.movieId = movieId;
    }

    public ZonedDateTime getInteractionTime() {
        return interactionTime;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }
}