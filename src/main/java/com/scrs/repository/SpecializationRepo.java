package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.scrs.model.SpecializationModel;

import jakarta.transaction.Transactional;

public interface SpecializationRepo extends JpaRepository<SpecializationModel, UUID> {
	@Query("SELECT spec FROM SpecializationModel spec WHERE spec.sn = ?1")
	SpecializationModel findBySN(String sn);

	@Query("SELECT spec FROM SpecializationModel spec WHERE spec.id = ?1")
	SpecializationModel findByID(UUID specId);
	
	@Modifying
	@Transactional
    @Query("UPDATE SpecializationModel s SET s.studentCount = s.studentCount + 1 WHERE s.id = ?1")
    void incrementStudentCount(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE SpecializationModel s SET s.studentCount = s.studentCount - 1 WHERE s.id = ?1")
    void decrementStudentCount(UUID id);
}
