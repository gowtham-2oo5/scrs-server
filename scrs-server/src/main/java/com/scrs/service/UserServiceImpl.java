package com.scrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrs.dao.UserDAO;
import com.scrs.model.UserModel;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public void addUser(UserModel user) {
		userDAO.addUser(user);
	}

	@Override
	public List<UserModel> getAllUsers() {
		return userDAO.getAllUsers();
	}
}
