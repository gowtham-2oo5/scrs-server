package com.scrs.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scrs.model.BatchModel;
import com.scrs.model.StudentModel;

public interface BatchRepo extends JpaRepository<BatchModel, UUID> {
	@Query("SELECT b.students from BatchModel b WHERE b.name = ?1")
	List<StudentModel> getStudentsOfBatch(String name);

	@Query("SELECT b from BatchModel b WHERE b.name = ?1")
	public BatchModel getBatchFromName(String name);

}
