// TODO: Un comment mailSender lines before review or shit
package com.scrs.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrs.model.UserModel;
import com.scrs.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public void addUser(UserModel user) {
		userRepo.save(user);
	}

	@Override
	public List<UserModel> getAllUsers() {
		List<UserModel> users = userRepo.findAll();
		System.out.println("Fetched Data: ");
		users.forEach(user -> System.out.println(user));
		return users;
	}

	@Override
	public UserModel getUserById(UUID id) {
		return userRepo.findById(id).get();
	}

}
