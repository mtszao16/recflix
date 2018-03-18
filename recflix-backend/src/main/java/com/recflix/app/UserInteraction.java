package com.recflix.app;

public class UserInteraction {

    private final String interationTime;
    private final String interactionType;
    private final String userId;

    public UserInteraction(String interationTime, String interactionType, String userId) {
        this.interationTime = interationTime;
        this.interactionType = interactionType;
        this.userId = userId;
    }

    public String getInterationTime() {
        return interationTime;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public String getUserId() {
        return userId;
    }
}