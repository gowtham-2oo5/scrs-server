package com.scrs.service;

import java.util.List;

import com.scrs.model.UserModel;

public interface UserService {

	public void addUser(UserModel user);
	public List<UserModel> getAllStudents();
}
