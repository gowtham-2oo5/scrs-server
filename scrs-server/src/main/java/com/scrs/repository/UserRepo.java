package com.scrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scrs.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {
    // No need to implement anything, Spring Data JPA will provide the implementation.
}
