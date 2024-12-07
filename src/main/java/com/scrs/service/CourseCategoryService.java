package com.scrs.service;

import com.scrs.dto.CourseCategoryDTO;
import com.scrs.model.CourseCategory;
import com.scrs.model.CourseModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseCategoryService {

    // Insert Single
    CourseCategory insertSingle(CourseCategoryDTO dto);

    // Bulk upload
    public String bulkUpload(MultipartFile file) throws IOException;

    // Retrieve all
    public List<CourseCategory> getAll();


    // Delete by category title
    public String deleteCourseCategory(String title);

    CourseCategory getCcByTitle(String courseCategory);


    void updateCC(CourseCategory category, CourseModel course);

    void updateSessions(CourseCategory category);


    CourseCategory getById(String courseCategory);
}
