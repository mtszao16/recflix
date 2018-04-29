package com.recflixEngine.app;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DBUtils
 */
public class DBUtils {

    private static MongoCollection<Document> userInteractions;
    private static MongoCollection<Document> users;
    private static MongoCollection<Document> movies;
    private static MongoCollection<Document> feedbacks;

    public DBUtils(MongoCollection<Document> userInteractions, MongoCollection<Document> users,
            MongoCollection<Document> movies, MongoCollection<Document> feedbacks) {
        DBUtils.userInteractions = userInteractions;
        DBUtils.users = users;
        DBUtils.movies = movies;
        DBUtils.feedbacks = feedbacks;
    }

    /**
     * interactionType ${String}
     * - forward
     * - backward
     * - forward seek
     * - backward seek
     * 
     * Other Implicit actions recorded:
     *  - Movie View Count for each user
     *  - Time spent watching a content
      */

    /********* WIP *********/

    public static List<WeightActions> getWeightedActions() {
        FindIterable<Document> allUsers = users.find();
        FindIterable<Document> allMovies = movies.find();

        List<WeightActions> wts = new ArrayList<WeightActions>();
        for (Document user : allUsers) {
            List<Document> watchedMoviesDocs = (List<Document>) users.find(Filters.eq("_id", user.get("_id")))
                    .projection(fields(include("watchedMovies"))).map(document -> document.get("watchedMovies"))
                    .first();

            if (watchedMoviesDocs != null) {
                for (Document movie : watchedMoviesDocs) {
                    Integer explicitRating = 0;
                    FindIterable<Document> feedbacksDocs = feedbacks
                            .find(Filters.and(Filters.eq("userId", user.get("_id").toString()),
                                    Filters.eq("movieId", movie.get("_id").toString())));
                    for (Document feedbacksDoc : feedbacksDocs) {
                        explicitRating = feedbacksDoc.getInteger("rating");
                    }
                    FindIterable<Document> allInteractions = userInteractions
                            .find(Filters.and(Filters.eq("interactedBy", user.get("_id").toString()),
                                    Filters.eq("movieId", movie.get("_id").toString())));
                    Integer backCntrlIntrCount = 0;
                    Integer fwdCntrlIntrCount = 0;
                    Integer backSeekIntrDuration = 0;
                    Integer fwdSeekIntrDuration = 0;

                    for (Document interaction : allInteractions) {
                        switch (interaction.getString("interactionType")) {
                        case "forward":
                            fwdCntrlIntrCount += 1;
                            break;
                        case "backward":
                            backCntrlIntrCount += 1;
                            break;
                        case "forward seek":
                            fwdSeekIntrDuration += interaction.getInteger("amount");
                            break;
                        case "backward seek":
                            backSeekIntrDuration += interaction.getInteger("amount");
                            break;

                        default:
                            break;
                        }
                    }

                    double timeSpendWt = (double) movie.getInteger("watchedDuration")
                            / (double) movie.getInteger("totalDuration");
                    double bckCntrlWt = Math.min(0.1 * backCntrlIntrCount, 0.5);
                    double fwdCntrlWt = Math.min(0.1 * fwdCntrlIntrCount, 0.5);
                    double bckSeekWt = 0.5
                            * ((double) backSeekIntrDuration / (double) movie.getInteger("totalDuration"));
                    ;
                    double fwdSeekWt = 0.5
                            * ((double) fwdSeekIntrDuration / (double) movie.getInteger("totalDuration"));
                    ;
                    double viewWt = 1.0;

                    wts.add(new WeightActions(user.get("_id").toString(), movie.get("_id").toString(),
                            (double) explicitRating, 0, 0.5, 0, 0.5, timeSpendWt, bckCntrlWt, fwdCntrlWt, bckSeekWt,
                            fwdSeekWt, viewWt));
                }
            }
        }
        return wts;
    }
}
