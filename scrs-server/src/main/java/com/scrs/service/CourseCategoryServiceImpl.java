package com.scrs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.CourseCategoryDTO;
import com.scrs.model.CourseCategory;
import com.scrs.model.CourseModel;
import com.scrs.repository.CourseCategoryRepo;

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
        String[] headers = { "title", "descr" }; // Updated headers for description
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
    public void updateCC(CourseCategory category, CourseModel course) {
        category.addCourse(course);

        updateSessions(category);
    }
}
