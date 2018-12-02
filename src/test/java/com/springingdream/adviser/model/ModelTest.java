package com.springingdream.adviser.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelTest {

    @Test
    public void ClusterDistanceTest() {

        UserPreferences user1 = new UserPreferences(0);
        user1.addProductRating(1L, 5);
        user1.addProductRating(2L, 2);
        user1.addProductRating(3L, 4);

        UserPreferences user2 = new UserPreferences(1);
        user2.addProductRating(1L, 4);
        user2.addProductRating(3L, 4);

        Cluster cluster = new Cluster();

        cluster.add(user1);
        assertEquals(0.0, cluster.calcDistance(user1), .001);

        cluster.add(user2);
        // centroid for cluster now is ("1": 4.5, "2": 1.0, "3": 4.0)
        assertEquals(1.118, cluster.calcDistance(user1), .001);
    }
}
