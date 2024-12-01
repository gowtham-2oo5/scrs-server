package com.scrs.service;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.CourseCreationDTO;
import com.scrs.model.CourseModel;
import com.scrs.model.DepartmentModel;
import com.scrs.model.SpecializationModel;

public interface CourseService {

	// Single upload
	public CourseModel singleCreate(CourseCreationDTO course);

	// Bulk upload
	public String bulkUploadCourses(MultipartFile file);

	// Retrieve all
	public List<CourseModel> getAll();

	// Retrieve all from course category
	// Update LTPS structure, Department, specialization and other fields
	// 1. Update LTPS
	public String updateLTPS(UUID id, Double L, Double T, Double P, Double S);

	// 2. Update FacultyIncharge
	public String updateCC(UUID id, String empId);

	// 3. Update course title
	public String updateCourseTitle(UUID id, String title);

	// Delete by id
	public String deleteCourse(UUID id);

	// get TargetDepts, targetSpecializations
	public List<DepartmentModel> getTargetDepts();

	public List<SpecializationModel> getTargetSpecs();

}
