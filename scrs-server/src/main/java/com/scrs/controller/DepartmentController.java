package com.scrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/dept")
@SecurityRequirement(name = "bearerAuth")
public class DepartmentController {

	@Autowired
	private DepartmentService deptService;

	@PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
	@PreAuthorize("hasRole('ADMIN')")
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
			System.out.println("From reqs: "+dept.toString());
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

	@PutMapping("/set-hod")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> setHod(@RequestParam String sn, @RequestParam String hodId) {
	    System.out.println("Request to update HOD for department with SN: " + sn);

	    if (sn == null || sn.isEmpty() || hodId == null || hodId.isEmpty()) {
	        System.out.println("Invalid input: sn=" + sn + ", hodId=" + hodId);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid department SN or HOD ID.");
	    }

	    try {
	        deptService.setHOD(sn, hodId);
	        System.out.println("Successfully updated HOD for department SN: " + sn);
	        return ResponseEntity.ok("HOD updated successfully.");
	    } catch (EntityNotFoundException ex) {
	        System.out.println("Department not found: " + ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
	    } catch (Exception ex) {
	        System.out.println("Unexpected error while updating HOD: " + ex.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error updating HOD: " + ex.getLocalizedMessage());
	    }
	}
	
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteDept(@RequestParam String id){
		try {
			deptService.deleteDept(id);
			return ResponseEntity.ok().body("DEleted dept successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("ERROR DELETING DEPT");
		}
	} 



}
