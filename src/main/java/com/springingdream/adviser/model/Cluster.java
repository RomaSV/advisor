package com.springingdream.adviser.model;

import java.util.*;

public class Cluster {

    private long id;
    private List<UserPreferences> users;
    private Map<Long, Double> centroid;

    public Cluster() {
        users = new ArrayList<>();
        centroid = new HashMap<>();
    }

    public void add(UserPreferences userPreferences) {
        users.add(userPreferences);
        calcCentroid();
    }

    public void remove(UserPreferences userPreferences) {
        users.remove(userPreferences);
        calcCentroid();
    }

    public double calcDistance(UserPreferences user) {

        double distance = 0.0;

        for (long product: user.getPreferences().keySet()) {

            if (!centroid.containsKey(product)) continue;

            double difference = user.getProductRating(product) - centroid.get(product);
            distance += difference * difference;
        }

        distance = Math.sqrt(distance);

        return distance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<UserPreferences> getUsers() {
        return users;
    }

    public int getSize() {
        return users.size();
    }

    private void calcCentroid() {
        for (UserPreferences user : users) {
            Set<Long> products = user.getPreferences().keySet();

            for (long product : products) {
                if (centroid.containsKey(product)) {
                    centroid.put(product, centroid.get(product) + user.getProductRating(product));
                } else {
                    centroid.put(product, (double)user.getProductRating(product));
                }
            }

            for (long product: centroid.keySet()) {
                centroid.put(product, centroid.get(product) / users.size());
            }
        }
    }

    @Override
    public String toString() {
        return "Cluster with id " + id + " contains: " + users.toString();
    }

}
