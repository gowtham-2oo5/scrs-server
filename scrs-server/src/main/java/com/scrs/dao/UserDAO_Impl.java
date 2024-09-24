package com.scrs.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scrs.model.UserModel;
import com.scrs.repository.UserRepo;

public class UserDAO_Impl implements UserDAO {

	@Autowired
	private UserRepo userRepo;

	@Override
	public void addUser(UserModel user) {
		userRepo.save(user);
		System.out.println("User added successfully");
	}

	@Override
	public List<UserModel> getAllUsers() {
		List<UserModel> users = userRepo.findAll();
		System.out.println("Fetched Data: ");
		users.forEach(user -> System.out.println(user));
		return users;
	}

}
