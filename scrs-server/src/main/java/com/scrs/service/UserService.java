package com.scrs.service;

import java.util.List;
import java.util.UUID;

import com.scrs.model.UserModel;

public interface UserService {

	public void addUser(UserModel user);

	public List<UserModel> getAllUsers();

	public String authenticate(String username, String password);

	public void sendOtp(String mail);

	public Object verifyOtp(String otp);

	public UserModel getUserById(UUID id);
}
