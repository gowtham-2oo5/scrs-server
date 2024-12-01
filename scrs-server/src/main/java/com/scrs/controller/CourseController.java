package com.scrs.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.CourseCreationDTO;
import com.scrs.model.CourseModel;
import com.scrs.model.DepartmentModel;
import com.scrs.model.SpecializationModel;
import com.scrs.service.CourseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/course")
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Endpoint to create a single course
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCourse(@RequestBody CourseCreationDTO dto) {
        try {
            CourseModel course = courseService.singleCreate(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating course: " + e.getLocalizedMessage());
        }
    }

    // Endpoint to bulk upload courses
    @PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkUploadCourses(@RequestPart("csv_file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            String response = courseService.bulkUploadCourses(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading courses: " + e.getLocalizedMessage());
        }
    }

    // Endpoint to get all courses
    @GetMapping("/get-all")
    public ResponseEntity<List<CourseModel>> getAllCourses() {
        try {
            List<CourseModel> courses = courseService.getAll();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Endpoint to update L-T-P-S
    @PutMapping("/update-ltps")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateLTPS(@RequestParam UUID id, @RequestParam Double L, @RequestParam Double T,
                                        @RequestParam Double P, @RequestParam Double S) {
        try {
            String response = courseService.updateLTPS(id, L, T, P, S);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating L-T-P-S: " + e.getLocalizedMessage());
        }
    }

    // Endpoint to update course incharge
    @PatchMapping("/update-incharge")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCourseIncharge(@RequestParam UUID id, @RequestParam String empId) {
        try {
            String response = courseService.updateCC(id, empId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating course incharge: " + e.getLocalizedMessage());
        }
    }

    // Endpoint to update course title
    @PatchMapping("/update-title")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCourseTitle(@RequestParam UUID id, @RequestParam String title) {
        try {
            String response = courseService.updateCourseTitle(id, title);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating course title: " + e.getLocalizedMessage());
        }
    }

    // Endpoint to delete a course
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(@RequestParam UUID id) {
        try {
            String response = courseService.deleteCourse(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting course: " + e.getLocalizedMessage());
        }
    }

    // Endpoint to get target departments
    @GetMapping("/target-departments")
    public ResponseEntity<List<DepartmentModel>> getTargetDepartments() {
        try {
            List<DepartmentModel> departments = courseService.getTargetDepts();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Endpoint to get target specializations
    @GetMapping("/target-specializations")
    public ResponseEntity<List<SpecializationModel>> getTargetSpecializations() {
        try {
            List<SpecializationModel> specializations = courseService.getTargetSpecs();
            return ResponseEntity.ok(specializations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
