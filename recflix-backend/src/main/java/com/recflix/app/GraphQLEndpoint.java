package com.recflix.app;

import com.coxautodev.graphql.tools.SchemaParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import graphql.servlet.SimpleGraphQLServlet;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;

import java.util.Optional;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@WebServlet(urlPatterns = "/graphqlApi")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final UserRepository userRepository;
    private static final UserInteractionRepository userInteractionRepository;
    static {
        MongoDatabase mongo = new MongoClient().getDatabase("recflix");
        userRepository = new UserRepository(mongo.getCollection("users"));
        userInteractionRepository = new UserInteractionRepository(mongo.getCollection("userInteractions"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser().file("schema.graphqls")
                .resolvers(new Query(userRepository, userInteractionRepository),
                        new Mutation(userRepository, userInteractionRepository), new SigninResolver(),
                        new UserInteractionResolver(userRepository))
                .build().makeExecutableSchema();
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request,
            Optional<HttpServletResponse> response) {
        User user = request.map(req -> req.getHeader("Authorization")).filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", "")).map(userRepository::findById).orElse(null);
        return new AuthContext(user, request, response);
    }
}