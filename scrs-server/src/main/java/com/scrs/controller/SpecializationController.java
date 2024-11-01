package com.scrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.SpcRegsDTO;
import com.scrs.model.SpecializationModel;
import com.scrs.service.SpecializationService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/spec")
public class SpecializationController {
	@Autowired
	private SpecializationService specService;

	@PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
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

	@PostMapping("/insert-one")
	public ResponseEntity<String> uploadOne(@RequestBody SpcRegsDTO spec) {
		try {
			specService.insertSpec(spec);
			return ResponseEntity.status(HttpStatus.OK).body("Hmm OK");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inserting dept" + e.getLocalizedMessage());
		}
	}

	@GetMapping("/get-all")
	public List<SpecializationModel> getAllDepts() {
		return specService.getAll();
	}

}
