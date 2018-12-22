package com.springingdream.adviser.service;

import com.springingdream.adviser.model.UserPreferences;
import com.springingdream.adviser.payload.UserProduct;

import java.util.ArrayList;
import java.util.List;

public class ClassificationUtils {
    /**
     * Similarity function for two users.
     * The order of values in `preferences` and `otherPreferences` doesn't matter. The result will be the same for the same pair.
     *
     * @param preferences      - one of the users whose similarity we want to calculate.
     * @param otherPreferences - another user  whose similarity we want to calculate.
     * @return - the bigger return value is - the more similar user are.
     */
    public static double calcSimilarity(UserPreferences preferences, UserPreferences otherPreferences) {
        double result = 1.0; // To prevent division by zero in case of 100% similarity

        List<UserProduct> commonProducts = new ArrayList<>();

        for (UserProduct product : preferences.getPreferences().keySet()) {
            for (UserProduct otherProduct : otherPreferences.getPreferences().keySet()) {
                if (product.equals(otherProduct)) {
                    commonProducts.add(product);
                    break;
                }
            }
        }

        for (UserProduct product : commonProducts) {
            result += Math.abs(preferences.getRating(product) - otherPreferences.getRating(product));
        }

        if (!commonProducts.isEmpty()) {
            result /= commonProducts.size();
        }

        return 1 / result;
    }
}
