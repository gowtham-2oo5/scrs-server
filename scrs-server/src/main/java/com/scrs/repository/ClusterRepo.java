package com.scrs.repository;

import com.scrs.model.BatchModel;
import com.scrs.model.ClusterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ClusterRepo extends JpaRepository<ClusterModel, UUID> {

    @Query("SELECT c FROM ClusterModel c WHERE c.name = ?1 AND c.for_batch = ?2")
    ClusterModel findByNameAndForBatch(String name, BatchModel forBatch);
}
