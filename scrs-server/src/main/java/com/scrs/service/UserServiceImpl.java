package com.scrs.service;

import java.util.List;

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
        System.out.println("User added successfully");
    }

    @Override
    public List<UserModel> getAllStudents() {
        List<UserModel> users = userRepo.findAll();
        System.out.println("Fetched Data: ");
        users.forEach((s) -> System.out.println(s.toString()));
        return users;
    }
}
