package com.recflix.app;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final UserRepository userRepository;
    private final UserInteractionRepository userInteractionRepository;

    public Query(UserRepository userRepository,
            UserInteractionRepository userInteractionRepository) {
        this.userRepository = userRepository;
        this.userInteractionRepository = userInteractionRepository;
    }

    public List<User> allUsers() {
        return userRepository.getAllUsers();
    }

    public List<UserInteraction> allUserInteractions() {
        return userInteractionRepository.getAllInteractions();
    }
}