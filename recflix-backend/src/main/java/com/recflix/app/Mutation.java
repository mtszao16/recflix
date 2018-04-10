package com.recflix.app;

import com.recflix.utils.AuthUtils;
import com.recflix.utils.HashString;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.coxautodev.graphql.tools.GraphQLRootResolver;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

/**
 * Mutation root
 */
public class Mutation implements GraphQLRootResolver {

    private final UserRepository userRepository;
    private final UserInteractionRepository userInteractionRepository;
    private final MovieRepository movieRepository;
    private final FeedbackRepository feedbackRepository;

    public Mutation(UserRepository userRepository, UserInteractionRepository userInteractionRepository,
            MovieRepository movieRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.userInteractionRepository = userInteractionRepository;
        this.movieRepository = movieRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    public Movie addMovie(String name, String url) {
        Movie newMovie = new Movie(name, url);
        return movieRepository.saveMovie(newMovie);
    }

    public Feedback recordFeedback(Integer rating, String type, String movieId, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        Feedback newFeedback = new Feedback(rating, type, Instant.now().atZone(ZoneOffset.UTC),
                context.getUser().getId(), movieId);
        return feedbackRepository.saveFeedback(newFeedback);
    }

    public UserInteraction logUserInteraction(String type, String movieId, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        UserInteraction newUserInteraction = new UserInteraction(Instant.now().atZone(ZoneOffset.UTC), type,
                context.getUser().getId(), movieId);
        return userInteractionRepository.saveUserInteraction(newUserInteraction);
    }

    public SigninPayload signinUser(AuthData auth) {
        User user = userRepository.findByEmail(auth.getEmail());
        String hashedPassword = HashString.hashTheString(auth.getPassword());
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(AuthUtils.getAppSecret());
            token = JWT.create().withIssuer("auth0").withClaim("userId", user.getId()).sign(algorithm);
        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        if (user.getPassword().equals(hashedPassword)) {
            return new SigninPayload(token, user);
        }
        throw new GraphQLException("Invalid credentials");
    }
}
