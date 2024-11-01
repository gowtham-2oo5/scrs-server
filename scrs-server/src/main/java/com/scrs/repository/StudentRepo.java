package com.scrs.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scrs.model.StudentModel;

@Repository
public interface StudentRepo extends JpaRepository<StudentModel, UUID> {

	Optional<StudentModel> findById(UUID id);

}
