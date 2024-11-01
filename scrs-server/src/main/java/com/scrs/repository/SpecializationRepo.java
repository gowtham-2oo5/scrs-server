package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scrs.model.SpecializationModel;

public interface SpecializationRepo extends JpaRepository<SpecializationModel, UUID> {
	@Query("SELECT spec FROM SpecializationModel spec WHERE spec.sn = ?1")
	SpecializationModel findBySN(String sn);
}
