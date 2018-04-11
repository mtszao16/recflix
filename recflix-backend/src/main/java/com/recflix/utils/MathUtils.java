package com.recflix.utils;

/**
 * MathUtils
 */
public class MathUtils {

    /**
     * interactionType ${String}
     * - forward
     * - backward
     * - seek
     * - pause
     * - play
      */

    /********* WIP *********/

    public static double computeWeightedValueForUserInteraction(String type, String movieId, String userId,
            Integer amount) {

        switch (type) {
        case "forward":
            return Math.min(0.1 * 2.0, 0.5);

        case "backward":
            return Math.min(0.1 * 2.0, 0.5);

        case "forward seek":
            return Math.min(0.1 * 2.0, 0.5);

        case "backward seek":
            return Math.min(0.1 * 2.0, 0.5);

        default:
            break;
        }
        return 0;
    }
}
