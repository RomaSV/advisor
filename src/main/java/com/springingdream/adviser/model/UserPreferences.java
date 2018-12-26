package com.springingdream.adviser.model;

import com.springingdream.adviser.payload.UserProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


/**
 * Contains all the information about users preferences.
 * For now it's only products rated by user, however other fields may be added in the future (e.g. preferred categories).
 */
@NoArgsConstructor
@Data
public class UserPreferences {

    private int ownerId;

    private Map<UserProduct, Integer> preferences;

    private Cluster cluster;

    public UserPreferences(int owner) {
        ownerId = owner;
        preferences = new HashMap<>();
    }

    public Integer getRating(UserProduct product) {
        return preferences.get(product);
    }

    public boolean contains(UserProduct product) {
        return preferences.containsKey(product);
    }

    @Override
    public String toString() {
        return "User " + ownerId + ": " + preferences.toString();
    }
}