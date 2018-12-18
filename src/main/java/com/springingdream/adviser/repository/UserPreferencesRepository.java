package com.springingdream.adviser.repository;

import com.springingdream.adviser.model.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Integer> {
    @Modifying
    @Query("update UserPreferences user set user.cluster.id = :clusterID where user.ownerId = :id")
    int setCluster(@Param("clusterID") Long clusterId, @Param("id") Integer id);


    @Query("select user from UserPreferences user where user.cluster.id = :clusterID")
    List<UserPreferences> getClusterPreferences(@Param("clusterID") Long clusterId);

    @Query("select user.cluster.id from UserPreferences user where user.ownerId = :userID")
    long getUsersClusterId(@Param("userID") Integer userId);
}
