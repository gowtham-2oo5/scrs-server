package com.scrs.service;

import com.scrs.dto.CourseCategoryDTO;
import com.scrs.model.CourseCategory;
import com.scrs.model.CourseModel;
import com.scrs.repository.CourseCategoryRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryRepo ccRepo;

    @Autowired
    private CsvService csvService;

    @Override
    public CourseCategory insertSingle(CourseCategoryDTO dto) {
        if (ccRepo.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Course category with this title already exists");
        }
        // Convert DTO to Entity
        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setTitle(dto.getTitle());
        courseCategory.setDescription(dto.getDescr());
        courseCategory.setMaxSessionsPerWeek(0);
        courseCategory.setMinSessionsPerWeek(0);
        // Save the entity
        return ccRepo.save(courseCategory);
    }

    @Override
    public String bulkUpload(MultipartFile file) throws IOException {
        String[] headers = {"title", "descr"}; // Updated headers for description
        List<CourseCategory> courseCats = csvService.parseCsv(file, headers, record -> {
            String title = record.get("title");

            if (ccRepo.existsByTitle(title)) {
                System.out.println("Skipping existing category: " + title);
                return null; // Skip duplicate categories
            }
            CourseCategory cc = new CourseCategory();
            cc.setTitle(title);
            cc.setDescription(record.get("descr"));
            cc.setMaxSessionsPerWeek(0);
            cc.setMinSessionsPerWeek(0);
            return cc;
        });

        // Remove nulls (skipped categories)
        courseCats.removeIf(cc -> cc == null);

        ccRepo.saveAll(courseCats);
        return "Bulk upload completed successfully";
    }

    @Override
    public List<CourseCategory> getAll() {
        return ccRepo.findAll();
    }

    @Override
    public String deleteCourseCategory(String title) {
        CourseCategory category = ccRepo.getCCByTitle(title);
        if (category == null) {
            throw new IllegalArgumentException("Course category not found");
        }
        ccRepo.delete(category);
        return "Deleted successfully";
    }

    @Override
    public CourseCategory getCcByTitle(String courseCategory) {
        return ccRepo.getCCByTitle(courseCategory);
    }

    @Override
    public void updateSessions(CourseCategory category) {
        List<CourseModel> courses = category.getCourses();
        if (courses == null || courses.isEmpty()) {
            category.setMinSessionsPerWeek(0);
            category.setMaxSessionsPerWeek(0);
        } else {
            double minCredits = courses.stream()
                    .mapToDouble(CourseModel::getCredits)
                    .min()
                    .orElse(0);
            double maxCredits = courses.stream()
                    .mapToDouble(CourseModel::getCredits)
                    .max()
                    .orElse(0);
            category.setMinSessionsPerWeek((int) Math.ceil(minCredits));
            category.setMaxSessionsPerWeek((int) Math.ceil(maxCredits));
        }
        ccRepo.save(category);
    }

    @Override
    public CourseCategory getById(String courseCategory) {
        try {
            // Validate the input
            if (courseCategory == null || courseCategory.isEmpty()) {
                throw new IllegalArgumentException("Course category ID cannot be null or empty");
            }

            // Attempt to parse the UUID
            UUID courseCategoryId;
            try {
                courseCategoryId = UUID.fromString(courseCategory);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid UUID format for course category ID: " + courseCategory, e);
            }

            // Retrieve the course category by ID
            CourseCategory category = ccRepo.getReferenceById(courseCategoryId);

            // Check if the category is found
            if (category == null) {
                throw new EntityNotFoundException("Course category with ID " + courseCategory + " not found");
            }

            // Debugging log
            System.out.println("Successfully retrieved course category with ID: " + courseCategory);

            return category;

        } catch (Exception e) {
            // Debugging log for the exception
            System.err.println("Error occurred while retrieving course category by ID: " + courseCategory);
            e.printStackTrace();
            throw e; // Rethrow the exception to propagate it
        }
    }


    @Override
    public void updateCC(CourseCategory category, CourseModel course) {
        category.addCourse(course);

        updateSessions(category);
    }
}
