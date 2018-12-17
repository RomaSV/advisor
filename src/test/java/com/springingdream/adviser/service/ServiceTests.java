package com.springingdream.adviser.service;

import com.springingdream.adviser.model.UserPreferences;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        assertEquals(1.0, adviser.calcSimilarity(firstPreferences, secondPreferences), .001);
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

        List<Long> expected = new ArrayList<>();
        expected.add(1L);
        expected.add(2L);
        expected.add(7L);

        assertEquals(expected, adviser.recommend(thirdPreferences.getOwnerId(), preferences));
    }

    @Test
    public void ClusteringTest() {

        UserPreferences firstPreferences = new UserPreferences(0);
        firstPreferences.addProductRating(1L, 5);
        firstPreferences.addProductRating(2L, 2);
        firstPreferences.addProductRating(3L, 4);

        UserPreferences secondPreferences = new UserPreferences(1);
        secondPreferences.addProductRating(1L, 4);
        secondPreferences.addProductRating(3L, 4);

        UserPreferences thirdPreferences = new UserPreferences(2);
        thirdPreferences.addProductRating(5L, 3);

        UserPreferences fourthPreferences = new UserPreferences(3);
        fourthPreferences.addProductRating(4L, 5);
        fourthPreferences.addProductRating(5L, 2);

        adviser.setClusterSize(2);
        adviser.addUserToCluster(firstPreferences);
        adviser.addUserToCluster(thirdPreferences);
        adviser.addUserToCluster(secondPreferences);
        adviser.addUserToCluster(fourthPreferences);

        assertEquals(2, adviser.clusters.size());

        // first and second should be in one cluster and third and fourth in another
        if (adviser.clusters.get(0).getUsers().contains(firstPreferences)) {
            assertTrue(adviser.clusters.get(0).getUsers().contains(secondPreferences));
            assertTrue(adviser.clusters.get(1).getUsers().contains(thirdPreferences));
            assertTrue(adviser.clusters.get(1).getUsers().contains(fourthPreferences));
        } else {
            assertTrue(adviser.clusters.get(0).getUsers().contains(thirdPreferences));
            assertTrue(adviser.clusters.get(0).getUsers().contains(fourthPreferences));
            assertTrue(adviser.clusters.get(1).getUsers().contains(secondPreferences));
        }
        System.out.println(adviser.clusters);
    }

}
