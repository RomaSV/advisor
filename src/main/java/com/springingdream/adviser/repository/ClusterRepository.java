package com.springingdream.adviser.repository;

import com.springingdream.adviser.model.Cluster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClusterRepository extends MongoRepository<Cluster, Long> {
}
