package com.recflix.app;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;
import graphql.servlet.SimpleGraphQLServlet;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;

import java.util.Optional;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@WebServlet(urlPatterns = "/graphql")
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
                        new Mutation(userRepository, userInteractionRepository), new SigninResolver())
                .build().makeExecutableSchema();
    }
}