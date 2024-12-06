package com.scrs.service;

import com.scrs.dto.ClusterDTO;
import com.scrs.model.ClusterModel;
import com.scrs.model.ScheduleTemplate;
import com.scrs.repository.ClusterRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ClusterRepo clusterRepo;

    @Autowired
    private BatchService batchService;

    @Autowired
    private ScheduleTemplateService scheduleTemplateService;

    @Autowired
    private CsvService csvService;

    @Override
    public List<ClusterDTO> getAllClusters() {
        List<ClusterModel> clusters = clusterRepo.findAll();
        if (clusters == null || clusters.isEmpty())
            return new ArrayList<>();

        List<ClusterDTO> clusterDTOs = new ArrayList<>();
        for (ClusterModel cluster : clusters) {
            clusterDTOs.add(mapToDTO(cluster));
        }
        return clusterDTOs;
    }

    @Override
    public ClusterDTO getClusterById(UUID id) {
        ClusterModel cluster = clusterRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cluster not found with ID: " + id));
        return mapToDTO(cluster);
    }

    @Override
    public ClusterDTO getClusterByNameAndBatch(String name, UUID batchId) {
        ClusterModel cluster = clusterRepo.findByNameAndForBatch(name, batchService.getById(batchId));
        if (cluster == null) {
            throw new EntityNotFoundException(
                    "Cluster not found with name: " + name + " and batch ID: " + batchId);
        }
        return mapToDTO(cluster);
    }

    @Override
    public void insertCluster(ClusterDTO clusterDTO) {
        ClusterModel cluster = new ClusterModel();
        cluster.setName(clusterDTO.getName());
        cluster.setFor_batch(batchService.getByName(clusterDTO.getBatch()));
        cluster.setStudents(new ArrayList<>());
        cluster.setSections(new ArrayList<>());
        cluster.setTemplate(getTemplate(clusterDTO.getTemplateId()));
        clusterRepo.save(cluster);
    }

    @Override
    public void bulkInsertClusters(MultipartFile file) throws IOException {
        String[] headers = {"Name", "Batch", "Template ID"};
        List<ClusterModel> clusters = csvService.parseCsv(file, headers, record -> {
            ClusterModel cluster = new ClusterModel();
            cluster.setName(record.get("Name"));
            cluster.setFor_batch(batchService.getByName(record.get("Batch")));
            cluster.setTemplate(getTemplate(UUID.fromString(record.get("Template ID"))));
            return cluster;
        });

        saveClusters(clusters);
    }

    private void saveClusters(List<ClusterModel> clusters) {
        for (ClusterModel cluster : clusters) {
            clusterRepo.save(cluster);
            System.out.println("Saved Cluster with ID: " + cluster.getId());
        }
    }

    @Override
    public void updateCluster(UUID id, ClusterDTO updatedClusterDTO) {
        ClusterModel existingCluster = clusterRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cluster not found with ID: " + id));

        if (updatedClusterDTO.getName() != null) {
            existingCluster.setName(updatedClusterDTO.getName());
        }
        if (updatedClusterDTO.getBatch() != null) {
            existingCluster.setFor_batch(batchService.getByName(updatedClusterDTO.getBatch()));
        }
        if (updatedClusterDTO.getTemplateId() != null) {
            existingCluster.setTemplate(getTemplate(updatedClusterDTO.getTemplateId()));
        }

        clusterRepo.save(existingCluster);
        System.out.println("Updated Cluster with ID: " + id);
    }

    @Override
    public void deleteCluster(UUID id) {
        ClusterModel cluster = clusterRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cluster not found with ID: " + id));
        clusterRepo.delete(cluster);
        System.out.println("Deleted Cluster with ID: " + id);
    }

    private ScheduleTemplate getTemplate(UUID templateId) {
        return scheduleTemplateService.getScheduleTemplateById(templateId);
    }

    private ClusterDTO mapToDTO(ClusterModel cluster) {
        ClusterDTO clusterDTO = new ClusterDTO();
        clusterDTO.setId(cluster.getId());
        clusterDTO.setName(cluster.getName());
        clusterDTO.setBatch(cluster.getFor_batch().getName());
        clusterDTO.setStudentCount(cluster.getStudentCount());
        clusterDTO.setSectionsCount((long) (cluster.getSections() != null ? cluster.getSections().size() : 0));
        return clusterDTO;
    }
}
