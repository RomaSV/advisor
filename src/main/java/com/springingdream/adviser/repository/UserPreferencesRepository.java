package com.springingdream.adviser.repository;

import com.springingdream.adviser.model.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Integer> {
    @Modifying
    @Query("update UserPreferences user set user.cluster_id = :clusterID where user.id = :id")
    int setCluster(@Param("clusterID") Long clusterId, @Param("id") Integer id);
}
