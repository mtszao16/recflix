package com.recflixEngine.app;

import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.List;

public class App {
    static {
        MongoDatabase mongo = new MongoClient().getDatabase("recflix");
        new DBUtils(mongo.getCollection("userInteractions"), mongo.getCollection("users"),
                mongo.getCollection("movies"), mongo.getCollection("feedbacks"));
    }

    public static void main(String[] args) {
        List<WeightActions> wts = DBUtils.getWeightedActions();
        List<RatingStruc> rats = new ArrayList<RatingStruc>();
        for (WeightActions wt : wts) {
            Uarca uarca = new Uarca(wt);
            double finalRating = uarca.getFinalRating();
            DBUtils.saveFinalRatingsToDb(wt.getUserId(), wt.getMovieId(), finalRating);
            rats.add(new RatingStruc(wt.getUserId(), wt.getMovieId(), finalRating));
            /* System.out.println(wt.getExplicitRating() + " " + wt.getRecomdToUserWt() + " " + wt.getAddFavWt() + " "
                    + wt.getWatchLstWt() + " " + wt.getRemFavWt() + " " + wt.getTimeSpendWt() + " " + wt.getBckCntrlWt()
                    + " " + wt.getFwdCntrlWt() + " " + wt.getBckSeekWt() + " " + wt.getFwdSeekWt() + " "
                    + wt.getViewWt()); */
        }

        rats = CollaborativeFiltering.CosineSimilarityUser(rats);
        for (RatingStruc e : rats) {
            DBUtils.saveFinalRatingsToDb(e.getUserId(), e.getMovieId(), e.getFinalRating());
            // System.out.println(e.getUserId() + " " + e.getMovieId() + " " + e.getFinalRating());
        }
    }
}