package com.scrs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrs.dto.FacultyDTO;
import com.scrs.dto.FacultyRegsDTO;
import com.scrs.model.FacultyModel;
import com.scrs.service.FacultyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/api/faculty")
@SecurityRequirement(name = "bearerAuth")
public class FacultyController {

    @Autowired
    private FacultyService facService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllFac() {
        try {
            return ResponseEntity.ok().body(facService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by-dept")
    public ResponseEntity<?> getMethodName(@RequestParam String sn) {
        System.out.println("SN: " + sn);
        System.out.println("In getting fac by dept");
        try {
            List<?> res = facService.getFacultiesByDept(sn);
            System.out.println(res);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Didnt get faculty ra lafoot");
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getFacBySN(@RequestParam String empId) {
        try {
            FacultyDTO res = facService.getFacultyByEmpId(empId);
            System.out.println(res);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Didnt get faculty ra lafoot");
        }
    }

    @PostMapping("/single-insert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testInsert(@RequestBody FacultyRegsDTO facDTO) {
        try {
            UUID id = facService.createSingleFaculty(facDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Saved with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Somethng is fishy, Check all");
        }
    }

    @PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsvFile(@RequestPart("csv_file") MultipartFile file) {
        System.out.println("In controller");
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            System.out.println("Going to this service method");
            facService.bulkInsertDepts(file);
            return ResponseEntity.ok("CSV file uploaded and processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<String> createFaculty(@RequestPart("profilePicture") MultipartFile profilePicture,
                                                @RequestPart("facDetails") String facultyDetailsJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FacultyRegsDTO facDetails = objectMapper.readValue(facultyDetailsJson, FacultyRegsDTO.class);

            System.out.println("Faculty details: " + facDetails);
            System.out.println("Profile picture: " + profilePicture.getOriginalFilename());

            FacultyModel faculty = facService.createFaculty(facDetails, profilePicture);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("faculty created successfully, Heres the ID: " + faculty.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Somethng is fishy, Check all" + e.getLocalizedMessage());
        }
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<String> getfacByUsername(String username) {
        try {
            FacultyModel faculty = facService.findByUsername(username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Going to find username: " + username + " User: " + faculty.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while finding fac with username, cuz:  " + e.getLocalizedMessage());
            // TODO: handle exception
        }
    }

    @DeleteMapping("delete-fac/{username}")
    public ResponseEntity<String> deleteFaculty(String uname) {
        try {
            facService.delFac(uname);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Faculty of username:  " + uname);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while finding fac with username, cuz:  " + e.getLocalizedMessage());
        }
    }

}
