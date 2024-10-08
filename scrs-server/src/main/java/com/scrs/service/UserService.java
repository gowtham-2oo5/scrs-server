package com.scrs.service;

import java.util.List;

import com.scrs.model.UserModel;

public interface UserService {

	public void addUser(UserModel user);

	public List<UserModel> getAllUsers();

	public UserModel getUserById(int id);
	
	public String authenticate(String username, String password);
	
	public void sendOtp(String mail);
	
	public UserModel verifyOtp(String otp);
}
