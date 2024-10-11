package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scrs.model.AdminModel;

@Repository
public interface AdminRepo extends JpaRepository<AdminModel, UUID> {

}
