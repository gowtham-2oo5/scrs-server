package com.scrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrs.dto.FacultyRegsDTO;
import com.scrs.model.FacultyModel;
import com.scrs.service.FacultyService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/faculty")
public class FacultyController {

	@Autowired
	private FacultyService facService;

	@PostMapping("/test-insert")
	public ResponseEntity<String> testInsert(@RequestBody FacultyRegsDTO facDTO) {
		try {
			facService.addFaculty(facDTO);
			return ResponseEntity.status(HttpStatus.OK).body(facDTO.toString());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Somethng is fishy, Check all");
		}
	}

	@PostMapping(value = "/create", consumes = "multipart/form-data")
	public ResponseEntity<String> createFaculty(@RequestPart("profilePicture") MultipartFile profilePicture,
			@RequestPart("facDetails") String facultyDetailsJson) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			FacultyRegsDTO facDetails = objectMapper.readValue(facultyDetailsJson, FacultyRegsDTO.class);

			System.out.println("Faculty details: " + facDetails);
			System.out.println("Profile picture: " + profilePicture.getOriginalFilename());

			FacultyModel faculty = facService.createFaculty(facDetails, profilePicture);

			return ResponseEntity.status(HttpStatus.OK)
					.body("faculty created successfully, Heres the ID: " + faculty.getId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Somethng is fishy, Check all" + e.getLocalizedMessage());
		}
	}

	@GetMapping("/getByUsername/{username}")
	public ResponseEntity<String> getfacByUsername(String username) {
		try {
			FacultyModel faculty = facService.findByUsername(username);
			return ResponseEntity.status(HttpStatus.OK)
					.body("Going to find username: " + username + " User: " + faculty.toString());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error while finding fac with username, cuz:  " + e.getLocalizedMessage());
			// TODO: handle exception
		}
	}

	@DeleteMapping("delete-fac/{username}")
	public ResponseEntity<String> deleteFaculty(String uname) {
		try {
			facService.delFac(uname);
			return ResponseEntity.status(HttpStatus.OK)
					.body("Deleted Faculty of username:  " + uname);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error while finding fac with username, cuz:  " + e.getLocalizedMessage());
		}
	}

}
