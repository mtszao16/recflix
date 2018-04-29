package com.recflixEngine.app;

import java.util.*;
import java.lang.Math;

class CollaborativeFiltering {

    public static List<RatingStruc> CosineSimilarityUser(List<RatingStruc> ratingStrucsList) {

        ListIterator<RatingStruc> ltr1 = ratingStrucsList.listIterator();
        ListIterator<RatingStruc> ltr2 = ratingStrucsList.listIterator();
        ListIterator<RatingStruc> ltr3 = ratingStrucsList.listIterator();
        while (ltr1.hasNext()) {
            RatingStruc rat = ltr1.next();
            String userId = rat.getUserId();
            String movieId = rat.getMovieId();

            float upperNum = 0;
            float upperDenom = 0;
            float finalRating = 0;
            while (ltr2.hasNext()) {
                RatingStruc _Rat = ltr2.next();
                double num = 0;
                double denom1 = 0;
                double denom2 = 0;
                boolean flag = false;
                String _userId = _Rat.getUserId();
                String _movieId = _Rat.getMovieId();
                double _finalRating = 0;

                if (_movieId.equalsIgnoreCase(movieId)) {
                    _finalRating = _Rat.getFinalRating();

                    while (ltr3.hasNext()) {
                        RatingStruc nextRat = ltr3.next();
                        if (nextRat.getUserId().equalsIgnoreCase(userId)) {
                            for (RatingStruc e : ratingStrucsList) {
                                if (e.getUserId().equalsIgnoreCase(_userId)) {
                                    if (e.getMovieId().equalsIgnoreCase(nextRat.getMovieId())) {
                                        flag = true;
                                        num += (double) nextRat.getFinalRating() * e.getFinalRating();
                                        denom1 += (double) nextRat.getFinalRating() * nextRat.getFinalRating();
                                        denom2 += (double) e.getFinalRating() * e.getFinalRating();
                                    }
                                }
                            }
                        }
                    }
                    // System.out.println(num + " " + denom1 + " " + denom2);
                    ltr3 = ratingStrucsList.listIterator();
                    if (flag) {
                        upperDenom += num / (Math.sqrt(denom1 * denom2));
                        upperNum += _finalRating * num / (Math.sqrt(denom1 * denom2));
                    }
                }

                float predrating = 0;

                if (upperDenom > 0) {
                    predrating = upperNum / upperDenom;
                } else {
                    predrating = upperNum;
                }
                finalRating = predrating;
            }
            ltr2 = ratingStrucsList.listIterator();
            ltr1.set(new RatingStruc(rat.getUserId(), rat.getMovieId(), finalRating));
        }

        return ratingStrucsList;
    }
}