package com.scrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrs.dto.AdminDTO;
import com.scrs.model.AdminModel;
import com.scrs.service.AdminService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("")
	public String testing() {
		return "Hello Ra Admin";
	}

	@PostMapping(value = "/create", consumes = "multipart/form-data")
	public ResponseEntity<String> createAdmin(@RequestPart("profilePicture") MultipartFile profilePicture,
			@RequestPart("adminDetails") String adminDetailsJson) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		AdminDTO adminDetails = objectMapper.readValue(adminDetailsJson, AdminDTO.class);

		System.out.println("Admin details: " + adminDetails);
		System.out.println("Profile picture: " + profilePicture.getOriginalFilename());

		try {
			AdminModel createdAdmin = adminService.createAdmin(adminDetails, profilePicture);

			return new ResponseEntity<>("Admin created successfully with ID: " + createdAdmin.getId(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error creating admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
