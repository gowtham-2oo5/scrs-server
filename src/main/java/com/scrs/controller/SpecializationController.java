package com.scrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.scrs.dto.SpcRegsDTO;
import com.scrs.model.SpecializationModel;
import com.scrs.service.SpecializationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/api/spec")
@SecurityRequirement(name = "bearerAuth")
public class SpecializationController {

	@Autowired
	private SpecializationService specService;

	// Bulk Upload Specializations
	@PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> uploadCsvFile(@RequestPart("csv_file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
		}

		try {
			specService.bulkInsertSpecs(file);
			return ResponseEntity.ok("CSV file uploaded and processed successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error processing file: " + e.getMessage());
		}
	}

	// Insert a Single Specialization
	@PostMapping("/insert-one")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> uploadOne(@RequestBody SpcRegsDTO spec) {
		try {
			specService.insertSpec(spec);
			return ResponseEntity.status(HttpStatus.OK).body("Specialization inserted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inserting specialization: " + e.getLocalizedMessage());
		}
	}

	// Fetch All Specializations
	@GetMapping("/get-all")
	public ResponseEntity<List<SpecializationModel>> getAllSpecs() {
		try {
			List<SpecializationModel> specs = specService.getAll();
			return ResponseEntity.ok(specs);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// Fetch a Single Specialization by SN
	@GetMapping("/get/{sn}")
	public ResponseEntity<SpecializationModel> getSpecBySn(@PathVariable String sn) {
		try {
			SpecializationModel spec = specService.getSpec(sn);
			if (spec == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(spec);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// Update a Specialization by SN
	@PutMapping("/update/{sn}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateSpec(@PathVariable String sn, @RequestParam String deptSn) {
		try {
			String response = specService.updateSpec(sn, deptSn);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating specialization: " + e.getMessage());
		}
	}

	// Delete a Specialization by ID
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteSpec(@PathVariable String id) {
		try {
			String response = specService.deleteSpec(id);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting specialization: " + e.getMessage());
		}
	}
}
