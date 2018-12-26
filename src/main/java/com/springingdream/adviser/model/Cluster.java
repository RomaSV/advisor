package com.springingdream.adviser.model;

import lombok.Data;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.TTest;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document
public class Cluster {

    private Long id;

    private List<User> users;

    private Map<Long, Double> portrait;

    private Map<Long, Integer> portraitSize;

    private transient Set<User> userSet = new HashSet<>();

    public Cluster() {
        users = new ArrayList<>();
        portrait = new HashMap<>();
        portraitSize = new HashMap<>();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        userSet = new HashSet<>(users);
    }

    public void add(Long pid, Double rate) {
        portrait.merge(pid, rate, (o, n) -> o + n );
        portraitSize.merge(pid, 1, (o, n) -> o + n);
    }

    public void addUser(Rater user) {
        users.add(new User(user.getUid()));
        userSet.add(new User(user.getUid()));
        for (Map.Entry<Long, Double> e : user.getRatings().entrySet()) {
            portrait.merge(e.getKey(), e.getValue(), (o, n) -> o + n );
            portraitSize.merge(e.getKey(), 1, (o, n) -> o + n);
        }
    }

    public Double getCost(Long pid) {
        if (portraitSize.get(pid) == null)
            return null;

        return portrait.get(pid) / portraitSize.get(pid);
    }

    public boolean contains(Long uid) {
        return userSet.contains(new User(uid));
    }

    public boolean closeEnough(Rater user) {
        List<Double> intersectionU = new ArrayList<>();
        List<Double> intersectionC = new ArrayList<>();
        int interSize = 0;
        for (Map.Entry<Long, Double> e : user.getRatings().entrySet())
            if (portrait.containsKey(e.getKey())) {
                intersectionU.add(e.getValue());
                intersectionC.add(portrait.get(e.getKey()) / portraitSize.get(e.getKey()));
                interSize++;
            }

        if (interSize > 4)
            return compareHuge(intersectionC, intersectionU);
        else
            return compareSmall(intersectionC, intersectionU);
    }

    private boolean compareHuge(List<Double> first, List<Double> second) {
        SummaryStatistics diff = new SummaryStatistics();
        for (int i = 0; i < first.size(); i++) {
            diff.addValue(first.get(i) - second.get(i));
        }

        return new TTest().tTest(0.0, diff, 0.07);
    }

    private boolean compareSmall(List<Double> first, List<Double> second) {
        double sum = 0;
        double deltaSum = 0;
        for (int i = 0; i < first.size(); i++) {
            sum += Math.abs(first.get(i));
            sum += Math.abs(second.get(i));
            deltaSum += first.get(i);
            deltaSum -= second.get(i);
        }

        return Math.abs(deltaSum) / sum / 2 < 0.1;
    }
}
