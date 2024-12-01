package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scrs.model.CourseCategory;

@Repository
public interface CourseCategoryRepo extends JpaRepository<CourseCategory, UUID> {
	@Query("SELECT cc FROM CourseCategory cc WHERE cc.title = ?1")
	public CourseCategory getCCByTitle(String title);

	public boolean existsByTitle(String title);
}
