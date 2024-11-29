package com.scrs.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrs.dto.StudentRegsDTO;
import com.scrs.model.StudentModel;
import com.scrs.service.StudentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/student")

@SecurityRequirement(name = "bearerAuth")
public class StudentController {

	@Autowired
	private StudentService studService;

	@GetMapping
	public ResponseEntity<List<StudentModel>> test() {
		try {
			return ResponseEntity.ok().body(studService.getAll());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
	@PreAuthorize("hasRole('ADMIN')")

	public ResponseEntity<String> uploadCsvFile(@RequestPart("csv_file") MultipartFile file) {
		System.out.println("In controller");
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
		}

		try {
			System.out.println("Going to this service method");
			studService.bulkInsertDepts(file);
			return ResponseEntity.ok("CSV file uploaded and processed successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error processing file: " + e.getMessage());
		}
	}

	@PostMapping(value = "/create", consumes = "multipart/form-data")
	@PreAuthorize("hasRole('ADMIN')")

	public ResponseEntity<String> createStudent(@RequestPart("profilePicture") MultipartFile profilePicture,
			@RequestPart("studentDetails") String stuDetails) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		StudentRegsDTO studDetails = objectMapper.readValue(stuDetails, StudentRegsDTO.class);

		System.out.println("Received Student details from client: " + studDetails);
		System.out.println("Profile picture: " + profilePicture.getOriginalFilename() + "\n Checking if null");
		System.out.println(profilePicture == null);
		try {
			StudentModel createdStudent = studService.createStudent(studDetails, profilePicture);
			System.out.println("Creating student: " + createdStudent.toString());
			return new ResponseEntity<>("Student created successfully with ID: " + createdStudent.getId(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error creating Student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("test-insert")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> createTest(@RequestBody StudentRegsDTO studentDTO) throws IOException {

		return new ResponseEntity<>(studService.createStudent(studentDTO, null).toString(), HttpStatus.OK);
	}
}
