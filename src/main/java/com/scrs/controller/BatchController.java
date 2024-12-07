package com.scrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.BatchRegsDTO;
import com.scrs.model.BatchModel;
import com.scrs.service.BatchService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

	@Autowired
	private BatchService bSer;

	// Bulk Upload CSV File
	@PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
	public ResponseEntity<String> uploadCsvFile(@RequestPart("csv_file") MultipartFile file) {
		System.out.println("Received bulk-upload request.");
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
		}

		try {
			bSer.bulkUpload(file);
			System.out.println("Bulk-upload successful.");
			return ResponseEntity.ok("CSV file uploaded and processed successfully");
		} catch (Exception e) {
			System.err.println("Error during bulk upload: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error processing file: " + e.getMessage());
		}
	}

	// Insert Single Batch
	@PostMapping("/insert-one")
	public ResponseEntity<String> uploadOne(@RequestBody BatchRegsDTO batch) {
		System.out.println("Received insert-one request: " + batch);
		try {
			bSer.insertOne(batch);
			return ResponseEntity.status(HttpStatus.OK).body("Batch inserted successfully");
		} catch (Exception e) {
			System.err.println("Error inserting batch: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inserting batch: " + e.getLocalizedMessage());
		}
	}

	// Get All Batches
	@GetMapping("/get-all")
	public ResponseEntity<List<BatchModel>> getAllBatches() {
		System.out.println("Received get-all request.");
		try {
			List<BatchModel> batches = bSer.getAll();
			System.out.println("Fetched all batches successfully.");
			return ResponseEntity.ok(batches);
		} catch (Exception e) {
			System.err.println("Error fetching batches: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Delete All Batches
	@DeleteMapping("/del-all")
	public ResponseEntity<String> deleteAll() {
		System.out.println("Received delete-all request.");
		try {
			bSer.delAll();
			System.out.println("Deleted all batches successfully.");
			return ResponseEntity.status(HttpStatus.OK).body("All batches deleted successfully.");
		} catch (Exception e) {
			System.err.println("Error deleting all batches: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting all batches: " + e.getLocalizedMessage());
		}
	}

	// Delete Batch by ID
	@DeleteMapping
	public ResponseEntity<String> deleteBatch(@RequestParam String id) {
		System.out.println("Received delete request for batch ID: " + id);
		if (id == null || id.isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Batch ID cannot be null or blank.");
		}
		try {
			bSer.deleteBatch(id);
			System.out.println("Deleted batch successfully: " + id);
			return ResponseEntity.status(HttpStatus.OK).body("Batch deleted successfully: " + id);
		} catch (Exception e) {
			System.err.println("Error deleting batch: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting batch: " + e.getLocalizedMessage());
		}
	}

	// Update Semester for a Batch
	@PutMapping("/update-sems/{batchName}")
	public ResponseEntity<String> updateSems(@PathVariable String batchName) {
		System.out.println("Received update-sems request for batch: " + batchName);
		if (batchName == null || batchName.isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Batch name cannot be null or blank.");
		}

		try {
			bSer.updateSems(batchName);
			System.out.println("Updated semester successfully for batch: " + batchName);
			return ResponseEntity.status(HttpStatus.OK).body("Semester updated successfully for batch: " + batchName);
		} catch (Exception e) {
			System.err.println("Error updating semester for batch: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating semester for batch: " + e.getLocalizedMessage());
		}
	}
}
