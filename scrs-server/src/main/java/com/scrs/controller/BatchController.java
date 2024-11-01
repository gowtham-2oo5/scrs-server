package com.scrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.BatchRegsDTO;
import com.scrs.model.BatchModel;
import com.scrs.service.BatchService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/batch")
public class BatchController {

	@Autowired
	private BatchService bSer;

	@PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
	public ResponseEntity<String> uploadCsvFile(@RequestPart("csv_file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
		}

		try {
			bSer.bulkUpload(file);
			return ResponseEntity.ok("CSV file uploaded and processed successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error processing file: " + e.getMessage());
		}
	}

	@PostMapping("/insert-one")
	public ResponseEntity<String> uploadOne(@RequestBody BatchRegsDTO batch) {
		try {
			bSer.insertOne(batch);
			return ResponseEntity.status(HttpStatus.OK).body("Hmm OK");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inserting dept" + e.getLocalizedMessage());
		}
	}

	@GetMapping("/get-all")
	public List<BatchModel> getAllDepts() {
		return bSer.getAll();
	}

	@DeleteMapping("/del-all")
	public ResponseEntity<String> deleteAll() {
		try {
			bSer.delAll();
			return ResponseEntity.status(HttpStatus.OK).body("Enjoy ra zuuka, Deleted all");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inserting dept" + e.getLocalizedMessage());
		}
	}

	@PutMapping("/update-sems/{batchName}")
	public ResponseEntity<String> updateSems(String name) {
		try {
			bSer.updateSems(name);
			return ResponseEntity.status(HttpStatus.OK).body("Enjoy ra zuuka, Updated semester for batch: " + name);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inserting dept" + e.getLocalizedMessage());
		}
	}
}
