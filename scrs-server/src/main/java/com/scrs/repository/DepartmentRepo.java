package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scrs.model.DepartmentModel;

public interface DepartmentRepo extends JpaRepository<DepartmentModel, UUID> {
	@Query("SELECT d FROM DepartmentModel d WHERE d.sn = ?1")
	DepartmentModel findBySN(String sn);

	@Query("SELECT d FROM DepartmentModel d WHERE d.id=?1")
	DepartmentModel findDeptById(UUID originalId);

}
