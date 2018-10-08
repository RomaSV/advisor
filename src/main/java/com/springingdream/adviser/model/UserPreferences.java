package com.springingdream.adviser.model;

import java.util.HashMap;
import java.util.Map;

public class UserPreferences {

    private Integer ownerId;
    private Map<Long, Integer> preferences;

    public UserPreferences(Integer owner) {
        ownerId = owner;
        preferences = new HashMap<>();
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Map<Long, Integer> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<Long, Integer> preferences) {
        this.preferences = preferences;
    }

    public void addProductRating(Long productId, Integer rating) {
        preferences.put(productId, rating);
    }

    public Integer getProductRating(Long productId) {
        return preferences.get(productId);
    }

    public boolean contains(Long productId) {
        return preferences.containsKey(productId);
    }
}