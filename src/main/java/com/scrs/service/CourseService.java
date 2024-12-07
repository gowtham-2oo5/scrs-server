package com.scrs.service;

import com.scrs.dto.CourseCreationDTO;
import com.scrs.dto.CourseDTO;
import com.scrs.dto.SectionDTO;
import com.scrs.model.CourseModel;
import com.scrs.model.DepartmentModel;
import com.scrs.model.SpecializationModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    // Single upload
    public CourseModel singleCreate(CourseCreationDTO course);

    // Bulk upload
    public String bulkUploadCourses(MultipartFile file);

    // Retrieve all
    public List<CourseDTO> getAll();


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

    public List<SpecializationModel> getTargetSpecs(UUID id);

    List<DepartmentModel> getTargetDepts(UUID id);

    List<SectionDTO> getSectionOfCourse(UUID id) throws Exception;

}
