package com.scrs.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrs.model.UserModel;
import com.scrs.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/test/roles")
	public List<String> getUserRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
		}
		return List.of("No roles found or user not authenticated");
	}

	@PostMapping("/add")
	public String addUser(@RequestBody UserModel user) {
		userService.addUser(user);
		return "User added";
	}

	@GetMapping("/get-user/{id}")
	public UserModel getUserById(@PathVariable UUID id) {
		return userService.getUserById(id);
	}

	@GetMapping("/get-all")
	public List<UserModel> getUsers() {
		return userService.getAllUsers();
	}

}