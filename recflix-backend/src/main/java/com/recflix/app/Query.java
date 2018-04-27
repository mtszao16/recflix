package com.recflix.app;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final UserRepository userRepository;
    private final UserInteractionRepository userInteractionRepository;
    private final MovieRepository movieRepository;
    private final FeedbackRepository feedbackRepository;

    public Query(UserRepository userRepository, UserInteractionRepository userInteractionRepository,
            MovieRepository movieRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.userInteractionRepository = userInteractionRepository;
        this.movieRepository = movieRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public List<User> allUsers() {
        return userRepository.getAllUsers();
    }

    public List<UserInteraction> allUserInteractions() {
        return userInteractionRepository.getAllInteractions();
    }

    public List<Movie> allMovies(MovieFilter filter) {
        return movieRepository.getAllMovies(filter);
    }

    public List<Feedback> allFeedbacks(FeedbackFilter filter) {
        return feedbackRepository.getAllFeedbacks(filter);
    }
}