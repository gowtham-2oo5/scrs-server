package com.scrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.DeptRegsDTO;
import com.scrs.model.DepartmentModel;
import com.scrs.service.DepartmentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/dept")
public class DepartmentController {

	@Autowired
	private DepartmentService deptService;

	@PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
	public ResponseEntity<String> uploadCsvFile(@RequestPart("csv_file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
		}

		try {
			deptService.bulkInsertDepts(file);
			return ResponseEntity.ok("CSV file uploaded and processed successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error processing file: " + e.getMessage());
		}
	}

	@PostMapping("/insert-one")
	public ResponseEntity<String> uploadOne(@RequestBody DeptRegsDTO dept) {
		try {
			deptService.insertDept(dept);
			return ResponseEntity.status(HttpStatus.OK).body("Hmm OK");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error inserting dept" + e.getLocalizedMessage());
		}
	}

	@GetMapping("/get-all")
	public List<DepartmentModel> getAllDepts() {
		return deptService.getAll();
	}
	
	@PutMapping("/set-hod/{sn}")
	public ResponseEntity<String> setHod(String sn,@RequestParam String uname){
		try {
			deptService.setHOD(sn, uname);
			return ResponseEntity.status(HttpStatus.OK).body("Updated HOD");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating dept HOD" + e.getLocalizedMessage());
		}
	}

}
