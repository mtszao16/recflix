package com.recflix.app;

import com.recflix.utils.HashString;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import graphql.GraphQLException;

/**
 * Mutation root
 */
public class Mutation implements GraphQLRootResolver {

    private final UserRepository userRepository;
    private final UserInteractionRepository userInteractionRepository;

    public Mutation(UserRepository userRepository, UserInteractionRepository userInteractionRepository) {
        this.userRepository = userRepository;
        this.userInteractionRepository = userInteractionRepository;
    }

    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    public UserInteraction logUserInteraction(String time, String type) {
        UserInteraction newUserInteraction = new UserInteraction(time, type);
        return userInteractionRepository.saveUserInteraction(newUserInteraction);
    }

    public SigninPayload signinUser(AuthData auth) {
        User user = userRepository.findByEmail(auth.getEmail());

        String hashedPassword = HashString.hashTheString(auth.getPassword());

        if (user.getPassword().equals(hashedPassword)) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }
}
