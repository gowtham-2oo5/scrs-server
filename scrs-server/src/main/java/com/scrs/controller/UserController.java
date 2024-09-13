package com.scrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrs.model.UserModel;
import com.scrs.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public String addUser(@RequestBody UserModel user) {
		userService.addUser(user);
		return "User added";
	}
	
	@GetMapping("/get-all")
	public List<UserModel> getUsers(){
		return userService.getAllStudents();
	}

}
