package com.scrs.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.scrs.model.BatchModel;
import com.scrs.model.StudentModel;

import jakarta.transaction.Transactional;

public interface BatchRepo extends JpaRepository<BatchModel, UUID> {
	@Query("SELECT b.students from BatchModel b WHERE b.name = ?1")
	List<StudentModel> getStudentsOfBatch(String name);

	@Query("SELECT b from BatchModel b WHERE b.name = ?1")
	public BatchModel getBatchFromName(String name);

	@Query("SELECT b from BatchModel b WHERE b.id = ?1")
	BatchModel findBatchById(UUID bId);

	@Modifying
	@Transactional
	@Query("UPDATE BatchModel b SET b.studentCount = b.studentCount + 1 WHERE b.id = ?1")
	void incrementStudentCount(UUID id);

	@Modifying
	@Transactional
	@Query("UPDATE BatchModel b SET b.studentCount = b.studentCount - 1 WHERE b.id = ?1")
	void decrementStudentCount(UUID id);
}
