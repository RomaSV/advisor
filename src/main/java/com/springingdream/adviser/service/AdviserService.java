package com.springingdream.adviser.service;

import com.springingdream.adviser.model.ClusterHeap;
import com.springingdream.adviser.model.Rater;
import com.springingdream.adviser.model.Rating;
import com.springingdream.adviser.repository.ClusterRepository;
import com.springingdream.adviser.repository.HistoryService;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdviserService {

    private final ClusterHeap ch;
    private final HistoryService service;
    private final ClusterRepository repository;

    @Autowired
    public AdviserService(ClusterRepository repository, HistoryService service) {
        this.ch = new ClusterHeap(repository.findAll());
        this.service = service;
        this.repository = repository;
    }

    public void addUser(Long uid) {
        List<Rating> ratings = service.ratings(uid);
        Rater rater = new Rater();
        rater.setUid(uid);

        Map<Long, Double> rate = new HashMap<>();
        for (Rating r : ratings)
            rate.put(r.getPid(), r.getRating().doubleValue());
        rater.setRatings(rate);
        repository.save(ch.addRater(rater));
    }

    public void update(Rating r) {
        repository.save(ch.update(r.getUid(), r.getPid(), r.getRating().doubleValue()));
    }

    /**
     * Gives recommendations to product based on ratings made by ALL other users.
     * Reasonable to use when user amount is not big enough to cluster them.
     *
     * @param userId - user that receives recommendations
     */
    public List<Long> getProductRecommendations(Long userId) {
        throw new NotImplementedException();
    }

    /**
     * Gives recommendations to user based on ratings made by other users IN USER'S CLUSTER.
     *
     * @param userId - user that receives recommendations
     */
    public List<Long> getUserRecommendations(Long userId) {
        return ch.getBestProductsForCluster(userId);
    }

}
