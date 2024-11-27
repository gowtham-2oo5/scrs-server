package com.scrs.service;

import java.util.List;
import java.util.UUID;

import com.scrs.model.UserModel;

public interface UserService {

	public void addUser(UserModel user);

	public List<UserModel> getAllUsers();

	public UserModel getUserById(UUID id);

}
