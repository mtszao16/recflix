package com.recflixEngine.app;

import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;

import java.util.List;

public class App {
    static {
        MongoDatabase mongo = new MongoClient().getDatabase("recflix");
        new MathUtils(mongo.getCollection("userInteractions"), mongo.getCollection("users"),
                mongo.getCollection("movies"), mongo.getCollection("feedbacks"));
    }

    public static void main(String[] args) {
        List<WeightActions> wts = MathUtils.getWeightedActions();
        for (WeightActions wt : wts) {
            Uarca uarca = new Uarca(wt);
            System.out.println(wt.getUserId() + " " + wt.getMovieId() + " " + +uarca.getFinalRating());
            System.out.println("\n");
            /* System.out.println(wt.getExplicitRating() + " " + wt.getRecomdToUserWt() + " " + wt.getAddFavWt() + " "
                    + wt.getWatchLstWt() + " " + wt.getRemFavWt() + " " + wt.getTimeSpendWt() + " " + wt.getBckCntrlWt()
                    + " " + wt.getFwdCntrlWt() + " " + wt.getBckSeekWt() + " " + wt.getFwdSeekWt() + " "
                    + wt.getViewWt()); */
        }
    }
}