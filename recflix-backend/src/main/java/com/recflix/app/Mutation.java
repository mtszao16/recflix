package com.recflix.app;

import com.recflix.utils.AuthUtils;
import com.recflix.utils.HashString;

import java.io.UnsupportedEncodingException;

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

    public Mutation(UserRepository userRepository, UserInteractionRepository userInteractionRepository) {
        this.userRepository = userRepository;
        this.userInteractionRepository = userInteractionRepository;
    }

    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    public UserInteraction logUserInteraction(String time, String type, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        UserInteraction newUserInteraction = new UserInteraction(time, type, context.getUser().getId());
        userInteractionRepository.saveUserInteraction(newUserInteraction);
        return newUserInteraction;
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
