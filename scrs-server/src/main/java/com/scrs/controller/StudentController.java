package com.scrs.controller;

import java.io.IOException;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrs.dto.StudentRegsDTO;
import com.scrs.model.StudentModel;
import com.scrs.service.StudentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studService;

	@GetMapping("")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("Working", HttpStatus.OK);
	}

	@PostMapping(value = "/create", consumes = "multipart/form-data")
	public ResponseEntity<String> createStudent(@RequestPart("profilePicture") MultipartFile profilePicture,
			@RequestPart("studentDetails") String stuDetails) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		StudentRegsDTO studDetails = objectMapper.readValue(stuDetails, StudentRegsDTO.class);

		System.out.println("Received Student details from client: " + studDetails);
		System.out.println("Profile picture: " + profilePicture.getOriginalFilename());

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
	public ResponseEntity<String> createTest(@RequestBody StudentRegsDTO studentDTO) throws IOException {

		return new ResponseEntity<>(studService.createStudent(studentDTO, null).toString(), HttpStatus.OK);
	}
}
