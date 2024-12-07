package com.scrs.controller;

import com.scrs.dto.CourseCategoryDTO;
import com.scrs.model.CourseCategory;
import com.scrs.service.CourseCategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/api/course-category")
@SecurityRequirement(name = "bearerAuth")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @PostMapping("/insert-one")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> insertOne(@RequestBody CourseCategoryDTO dto) {
        try {
            CourseCategory courseCategory = courseCategoryService.insertSingle(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(courseCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inserting course category: " + e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkUpload(@RequestPart("csv_file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            String response = courseCategoryService.bulkUpload(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getLocalizedMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CourseCategory>> getAllCourseCategories() {
        try {
            List<CourseCategory> courseCategories = courseCategoryService.getAll();
            if (courseCategories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(courseCategories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint to delete a single course category by title
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourseCategory(@RequestParam String title) {
        try {
            String response = courseCategoryService.deleteCourseCategory(title);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting course category: " + e.getLocalizedMessage());
        }
    }
}
