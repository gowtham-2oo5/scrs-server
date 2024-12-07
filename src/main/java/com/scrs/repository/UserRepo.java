package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scrs.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, UUID> {

    UserModel findByUsername(String username);
}