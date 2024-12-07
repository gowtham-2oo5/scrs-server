package com.scrs.service;

import com.scrs.dto.ClusterDTO;
import com.scrs.model.ClusterModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ClusterService {

    List<ClusterDTO> getAllClusters();

    ClusterDTO getClusterById(UUID id);

    ClusterDTO getClusterByNameAndBatch(String name, UUID batchId);

    void insertCluster(ClusterDTO clusterDTO);

    void bulkInsertClusters(MultipartFile file) throws IOException;

    void updateCluster(UUID id, ClusterDTO updatedClusterDTO);

    void setClusterTemplate(UUID clusterId, UUID templateId);

    void deleteCluster(UUID id);

    ClusterModel getClusterRefById(UUID uuid);
}
