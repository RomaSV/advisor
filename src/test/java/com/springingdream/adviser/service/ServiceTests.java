package com.springingdream.adviser.service;

import com.springingdream.adviser.model.UserPreferences;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ServiceTests {

    private AdviserService adviser = new AdviserService();

    @Test
    public void SimilarityTest() {
        UserPreferences firstPreferences = new UserPreferences(0);
        firstPreferences.addProductRating(1L, 5);
        firstPreferences.addProductRating(2L, 2);
        firstPreferences.addProductRating(3L, 4);
        firstPreferences.addProductRating(7L, 1);

        UserPreferences secondPreferences = new UserPreferences(1);
        secondPreferences.addProductRating(1L, 4);
        secondPreferences.addProductRating(2L, 1);
        secondPreferences.addProductRating(3L, 4);

        assertEquals(1.5, adviser.calcSimilarity(firstPreferences, secondPreferences), .001);
    }

    @Test
    public void SimpleRecommendTest() {

        List<UserPreferences> preferences = new ArrayList<>();

        UserPreferences firstPreferences = new UserPreferences(0);
        firstPreferences.addProductRating(1L, 5);
        firstPreferences.addProductRating(2L, 2);
        firstPreferences.addProductRating(3L, 4);
        firstPreferences.addProductRating(7L, 1);
        preferences.add(firstPreferences);

        UserPreferences secondPreferences = new UserPreferences(1);
        secondPreferences.addProductRating(1L, 4);
        secondPreferences.addProductRating(2L, 1);
        secondPreferences.addProductRating(3L, 4);
        preferences.add(secondPreferences);

        UserPreferences thirdPreferences = new UserPreferences(2);
        thirdPreferences.addProductRating(3L, 5);
        preferences.add(thirdPreferences);

        System.out.println(adviser.recommend(thirdPreferences.getOwnerId(), preferences));
    }

}
