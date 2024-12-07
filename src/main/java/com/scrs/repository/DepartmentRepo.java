package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.scrs.model.DepartmentModel;

import jakarta.transaction.Transactional;

public interface DepartmentRepo extends JpaRepository<DepartmentModel, UUID> {
	@Query("SELECT d FROM DepartmentModel d WHERE d.sn = ?1")
	DepartmentModel findBySN(String sn);

	@Query("SELECT d FROM DepartmentModel d WHERE d.id=?1")
	DepartmentModel findDeptById(UUID originalId);

	@Modifying
	@Transactional
	@Query("UPDATE DepartmentModel d SET d.studentCount = d.studentCount + 1 WHERE d.id = ?1")
	void incrementStudentCount(UUID id);

	@Modifying
	@Transactional
	@Query("UPDATE DepartmentModel d SET d.studentCount = d.studentCount - 1 WHERE d.id = ?1")
	void decrementStudentCount(UUID id);

}
