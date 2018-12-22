package com.springingdream.adviser.model;

import com.springingdream.adviser.payload.UserProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Contains all the information about users preferences.
 * For now it's only products rated by user, however other fields may be added in the future (e.g. preferred categories).
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "user_preferences")
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ownerId;

    @ElementCollection
    @CollectionTable(name = "user_ratings")
    @MapKeyColumn(name = "product_id")
    @Column(name = "rating")
    private Map<UserProduct, Integer> preferences;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
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