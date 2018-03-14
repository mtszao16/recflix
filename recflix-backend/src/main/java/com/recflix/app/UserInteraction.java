package com.recflix.app;

public class UserInteraction {

    private final String interationTime;
    private final String interactionType;

    public UserInteraction(String interationTime, String interactionType) {
        this.interationTime = interationTime;
        this.interactionType = interactionType;
    }

    public String getInterationTime() {
        return interationTime;
    }

    public String getInteractionType() {
        return interactionType;
    }
}