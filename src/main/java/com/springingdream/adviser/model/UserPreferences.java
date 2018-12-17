package com.springingdream.adviser.model;

import java.util.HashMap;
import java.util.Map;

public class UserPreferences {

    private int ownerId;
    private Map<Long, Integer> preferences;

    public UserPreferences(int owner) {
        ownerId = owner;
        preferences = new HashMap<>();
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Map<Long, Integer> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<Long, Integer> preferences) {
        this.preferences = preferences;
    }

    public void addProductRating(long productId, int rating) {
        preferences.put(productId, rating);
    }

    public int getProductRating(long productId) {
        return preferences.get(productId);
    }

    public boolean contains(long productId) {
        return preferences.containsKey(productId);
    }

    @Override
    public String toString() {
        return "User " + ownerId + ": " + preferences.toString();
    }
}