package com.scrs.dao;

import java.util.List;

import com.scrs.model.UserModel;

public interface UserDAO {

	public void addUser(UserModel user);
	public List<UserModel> getAllUsers();
	
}
