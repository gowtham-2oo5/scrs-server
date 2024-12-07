package com.scrs.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scrs.model.CourseModel;

@Repository
public interface CourseRepo extends JpaRepository<CourseModel, UUID> {

	@Query("SELECT co FROM CourseModel co WHERE co.id = ?1")
	CourseModel getCourseById(UUID preReqCourse);

	@Query("SELECT co FROM CourseModel co WHERE co.courseCode = ?1")
	CourseModel getCourseByCode(String preReqCourseCode);

}
