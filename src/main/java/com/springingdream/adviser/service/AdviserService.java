package com.springingdream.adviser.service;

import com.springingdream.adviser.model.Cluster;
import com.springingdream.adviser.model.Product;
import com.springingdream.adviser.model.UserPreferences;
import com.springingdream.adviser.payload.UserProduct;
import com.springingdream.adviser.repository.ClusterRepository;
import com.springingdream.adviser.repository.UserPreferencesRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdviserService {

    private final UserPreferencesRepository userPreferencesRepository;

    private final ClusterRepository clusterRepository;

    // Just for now it's static, but should be calculated dynamically for each cluster
    private int clusterSize = 25;
    private List<Cluster> clusters = new ArrayList<>();

    @Autowired
    public AdviserService(UserPreferencesRepository userPreferencesRepository, ClusterRepository clusterRepository) {

        this.userPreferencesRepository = userPreferencesRepository;
        this.clusterRepository = clusterRepository;
    }

    /**
     * Init clusters from database.
     * TODO: find a better way to do it
     *
     * @return true on success
     */
    public boolean init() {
        List<UserPreferences> userPreferences = getPreferences();
        for (UserPreferences user : userPreferences) {
            Cluster userCluser = user.getCluster();
            if (!clusters.contains(userCluser)) {
                clusters.add(userCluser);
            }
        }
//        System.out.println(clusters);
//        for (Cluster cluster : clusters) {
//            cluster.calcCentroid();
//            clusterRepository.save(cluster);
//        }
//        cluster(); //???
        return true;
    }

    /**
     * Gives recommendations to user based on ratings made by ALL other users.
     * Reasonable to use when user amount is not big enough to cluster them.
     *
     * @param userId - user that receives recommendations
     * @param page   - number of the current page of a recommendations list
     * @param size   - amount of recommendations on each page
     */
    public List<UserProduct> getGeneralRecommendations(int userId, int page, int size) {
        List<UserPreferences> preferences = getPreferences();
        return getGeneralRecommendations(userId, page, size, preferences);
    }

    /**
     * Gives recommendations to user based on ratings made by other users IN USER'S CLUSTER.
     *
     * @param userId - user that receives recommendations
     * @param page   - number of the current page of a recommendations list
     * @param size   - amount of recommendations on each page
     */
    public List<UserProduct> getGeneralRecommendationsByCluster(int userId, int page, int size) {
        List<UserPreferences> preferences = getPreferencesForCluster(userId);
        return getGeneralRecommendations(userId, page, size, preferences);
    }

    public List<UserProduct> getSimilar(UserProduct product, long userId, int page, int size) {
        //TODO collaboration filtering
        return null;
    }

    public List<UserProduct> getSimilar(UserProduct product, int page, int size) {
        //TODO one of content based methods.. or not?
        return null;
    }

    public List<UserProduct> getRelated(UserProduct product, int page, int size) {
        //TODO one of content based methods.. or not?
        return null;
    }

    /**
     * Cluster or recluster users.
     * Users with no rated products remain in their previous cluster.
     */
    public void cluster() {

        boolean changed = true;

        while (changed) {
            changed = false;

            List<List<UserPreferences>> clusterUsers = new ArrayList<>();

            for (Cluster cluster : clusters) {
                clusterUsers.add(new ArrayList<>(cluster.getUsers()));
            }

            for (int i = 0; i < clusters.size(); i++) {
                for (UserPreferences user : clusterUsers.get(i)) {
                    int minDistanceCluster = i;
                    double minDistance = clusters.get(minDistanceCluster).calcDistance(user);

                    for (int j = 0; j < clusters.size(); j++) {
                        if (j == i) continue;
                        double distance = clusters.get(j).calcDistance(user);
                        System.out.println("distance: " + distance);
                        if (distance < minDistance) {
                            minDistance = distance;
                            minDistanceCluster = j;
                        }
                    }

                    if (minDistanceCluster != i) {
                        clusters.get(i).remove(user);
                        Cluster newCluster = clusters.get(minDistanceCluster);
                        newCluster.add(user);

                        user.setCluster(newCluster);
                        clusterRepository.save(newCluster);
                        userPreferencesRepository.save(user);
                    }

                }
            }

        }
    }

    public void addUserToCluster(int userId) {
        getPreference(userId).ifPresent(this::addUserToCluster);
    }

    /**
     * A simple way to add a user to cluster.
     */
    void addUserToCluster(UserPreferences user) {
        boolean added = false;
        for (Cluster cluster : clusters) {
            if (cluster.getSize() < clusterSize) {
                cluster.add(user);
                userPreferencesRepository.setCluster(cluster.getId(), user.getOwnerId());
                added = true;
                break;
            }
        }

        if (!added) {
            Cluster newCluster = new Cluster();
            newCluster.setId(clusters.size());
            newCluster.add(user);
            userPreferencesRepository.setCluster(newCluster.getId(), user.getOwnerId());
            clusters.add(newCluster);
        }

        cluster();

    }

    private List<UserProduct> getGeneralRecommendations(int userId, UserPreferences targetedPrefs, List<UserPreferences> preferences) {
        List<UserProduct> recommendations = recommend(userId, targetedPrefs, preferences);

        if (recommendations == null) {
            return Collections.emptyList();
        }

        return recommendations;
    }

    /**
     * Collaboration filtering algorithm
     * TODO Java doc for this
     */
    List<UserProduct> recommend(
            int userId,
            @NotNull UserPreferences targetedPrefs,
            @NotNull List<UserPreferences> preferences) {
        Map<UserProduct, Double> rank = new HashMap<>();

        for (UserPreferences otherUserPref : preferences) {
            if (otherUserPref.getOwnerId() != userId) {
                double similarity = ClassificationUtils.calcSimilarity(targetedPrefs, otherUserPref);

                for (UserProduct productId : otherUserPref.getPreferences().keySet()) {
                    if (!targetedPrefs.contains(productId)) {
                        if (!rank.containsKey(productId)) {
                            rank.put(productId, 0.0);
                        }
                        double newValue = rank.get(productId) + similarity * otherUserPref.getRating(productId);
                        rank.put(productId, newValue);
                    }

                }
            }
        }

        List<UserProduct> result = new ArrayList<>();

        rank.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).forEach((entry) -> result.add(entry.));

        Collections.reverse(result);
        return result;
    }

    @TestOnly
    void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    /**
     * Get preferences of all users in the same cluster with given.
     *
     * @param userId - id of the user whose cluster preferences will be returned
     * @return - list of UserPreferences in the cluster
     */
    private List<UserPreferences> getPreferencesForCluster(int userId) {
        long clusterId = userPreferencesRepository.getUsersClusterId(userId);
        return userPreferencesRepository.getClusterPreferences(clusterId);
    }

    private List<UserPreferences> getPreferences() {
        return userPreferencesRepository.findAll();
    }

    private Optional<UserPreferences> getPreference(int userId) {
        return userPreferencesRepository.findById(userId);
    }

}
