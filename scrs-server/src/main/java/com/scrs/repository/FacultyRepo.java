package com.scrs.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scrs.model.DepartmentModel;
import com.scrs.model.FacultyModel;

@Repository
public interface FacultyRepo extends JpaRepository<FacultyModel, UUID> {
	@Query("SELECT f FROM FacultyModel f WHERE f.username = ?1")
	FacultyModel getByUsername(String uname);

	@Query("SELECT f FROM FacultyModel f WHERE f.department = :department")
	List<FacultyModel> getFacultiesByDept(@Param("department") DepartmentModel dept);

	@Query("SELECT f FROM FacultyModel f WHERE f.empId = ?1")
	FacultyModel findByEmpId(String hodId);
}
