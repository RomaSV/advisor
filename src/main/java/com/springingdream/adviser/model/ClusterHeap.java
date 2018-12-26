package com.springingdream.adviser.model;

import lombok.Data;

import java.util.*;

@Data
public class ClusterHeap {
    private List<Cluster> clusters;

    public ClusterHeap(List<Cluster> clusters) {
        this.clusters = new ArrayList<>(clusters);
    }

    public Cluster addRater(Rater rater) {
        Cluster result = null;
        boolean added = false;
        for (Cluster cluster : clusters) {
            if (cluster.closeEnough(rater)) {
                cluster.addUser(rater);
                added = true;
                result = cluster;
                break;
            }
        }

        if (!added) {
            Cluster c = new Cluster();
            c.addUser(rater);
            clusters.add(c);
            result = c;
        }
        return result;
    }

    public Cluster getUserCluster(Long uid) {
        for (Cluster cluster : clusters)
            if (cluster.contains(uid))
                return cluster;
        return null;
    }

    public Cluster update(Long uid, Long pid, Double rating) {
        Cluster c = getUserCluster(uid);
        if (c == null) {
            Rater r = new Rater();
            r.setUid(uid);
            Map<Long, Double> rate = new HashMap<>();
            rate.put(pid, rating);
            r.setRatings(rate);
            return addRater(r);
        }

        c.add(pid, rating);
        return c;
    }

    public List<Long> getBestProductsForCluster(Long uid) {
        class Good {
            private Good(long id, double rating) {
                this.id = id;
                this.rating = rating;
            }

            private long id;
            private double rating;

            private double getRating() {
                return rating;
            }
        }

        Cluster c = getUserCluster(uid);

        if (c == null)
            return new ArrayList<>();

        Map<Long, Double> products = c.getPortrait();
        Map<Long, Integer> productsWeights = c.getPortraitSize();

        List<Good> goods = new ArrayList<>();
        for (Map.Entry<Long, Double> p : products.entrySet()) {
            goods.add(new Good(p.getKey(), p.getValue() / productsWeights.get(p.getKey())));
        }
        goods.sort(Comparator.comparingDouble(Good::getRating).reversed());

        List<Long> result = new ArrayList<>();
        for (Good g : goods)
            result.add(g.id);
        return result;
    }
}
