package com.scrs.controller;

import com.scrs.dto.ClusterDTO;
import com.scrs.service.ClusterService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/api/clusters")
@SecurityRequirement(name = "bearerAuth")
public class ClusterController {

    @Autowired
    private ClusterService clusterService;

    /**
     * Get all clusters.
     *
     * @return List of all clusters.
     */
    @GetMapping
    public ResponseEntity<List<ClusterDTO>> getAllClusters() {
        List<ClusterDTO> clusters = clusterService.getAllClusters();
        return new ResponseEntity<>(clusters, HttpStatus.OK);
    }

    /**
     * Get a cluster by its ID.
     *
     * @param id UUID of the cluster.
     * @return ClusterDTO of the requested cluster.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClusterDTO> getClusterById(@PathVariable UUID id) {
        ClusterDTO cluster = clusterService.getClusterById(id);
        return new ResponseEntity<>(cluster, HttpStatus.OK);
    }

    /**
     * Get a cluster by its name and batch ID.
     *
     * @param name    Name of the cluster.
     * @param batchId UUID of the batch.
     * @return ClusterDTO of the requested cluster.
     */
    @GetMapping("/search")
    public ResponseEntity<ClusterDTO> getClusterByNameAndBatch(
            @RequestParam String name,
            @RequestParam UUID batchId) {
        ClusterDTO cluster = clusterService.getClusterByNameAndBatch(name, batchId);
        return new ResponseEntity<>(cluster, HttpStatus.OK);
    }

    /**
     * Create a new cluster.
     *
     * @param clusterDTO DTO representing the new cluster.
     * @return Status of the creation.
     */
    @PostMapping
    public ResponseEntity<String> createCluster(@RequestBody ClusterDTO clusterDTO) {
        clusterService.insertCluster(clusterDTO);
        return new ResponseEntity<>("Cluster created successfully.", HttpStatus.CREATED);
    }

    /**
     * Bulk insert clusters using a CSV file.
     *
     * @param file CSV file containing cluster data.
     * @return Status of the bulk insertion.
     * @throws IOException If an error occurs while reading the file.
     */
    @PostMapping("/bulk-insert")
    public ResponseEntity<String> bulkInsertClusters(@RequestParam("file") MultipartFile file) throws IOException {
        clusterService.bulkInsertClusters(file);
        return new ResponseEntity<>("Clusters inserted successfully.", HttpStatus.CREATED);
    }

    /**
     * Update an existing cluster.
     *
     * @param id         UUID of the cluster to update.
     * @param clusterDTO DTO containing updated details of the cluster.
     * @return Status of the update.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCluster(@PathVariable UUID id, @RequestBody ClusterDTO clusterDTO) {
        clusterService.updateCluster(id, clusterDTO);
        return new ResponseEntity<>("Cluster updated successfully.", HttpStatus.OK);
    }

    /**
     * Delete a cluster by its ID.
     *
     * @param id UUID of the cluster to delete.
     * @return Status of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCluster(@PathVariable UUID id) {
        clusterService.deleteCluster(id);
        return new ResponseEntity<>("Cluster deleted successfully.", HttpStatus.NO_CONTENT);
    }
}
