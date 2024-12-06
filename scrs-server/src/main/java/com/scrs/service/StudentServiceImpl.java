package com.scrs.service;

import com.scrs.dto.StudentMailDTO;
import com.scrs.dto.StudentRegsDTO;
import com.scrs.model.*;
import com.scrs.repository.BatchRepo;
import com.scrs.repository.DepartmentRepo;
import com.scrs.repository.SpecializationRepo;
import com.scrs.repository.StudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private BatchRepo bRepo;

    @Autowired
    private DepartmentRepo deptRepo;

    @Autowired
    private SpecializationRepo specRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordService passService;

    @Autowired
    private CsvService csvService;

    @Autowired
    private MailService mailService;

    @Override
    public StudentModel createStudent(StudentRegsDTO dto, MultipartFile profilePicture) throws IOException {
        String currPass = passService.generatePassword(8);
        String encryptedPassword = passwordEncoder.encode(currPass);
        System.out.println("Is null at service ?" + profilePicture == null);

        System.out.println("Going to create new Student");
        StudentModel student = new StudentModel();

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setContact(dto.getContact());
        student.setUsername(dto.getRegNum());
        student.setPassword(encryptedPassword);
        student.setRole(UserRole.STUDENT);
        student.setProfilePicture(profilePictureUrl);
        student.setRegNum(dto.getRegNum());
        student.setDob(dto.getDob());
        student.setContact(dto.getContact());

        // Set relationships
        SpecializationModel spec = getSpecialization(dto.getSpec());
        if (spec != null) {
            student.setSpecialization(spec);
        } else {
            System.err.println("NULL Specialization");
            return null;
        }

        DepartmentModel dept = getDept(dto.getDept());
        if (dept != null) {
            student.setDept(dept);
        } else {
            System.err.println("NULL Dept");
            return null;
        }

        BatchModel batch = getBatch(dto.getBatch());
        if (batch != null) {
            student.setBatch(batch);
        } else {
            System.err.println("NULL Batch");
            return null;
        }

        System.out.println("Got Student: " + student.getName());


        // Save student and increment counts
        studentRepo.save(student);

        StudentMailDTO studentDTO = new StudentMailDTO();
        studentDTO.setEmail(student.getEmail());
        studentDTO.setName(student.getName());
        studentDTO.setPassword(currPass);
        mailService.sendStudentAccountConfirmationMail(student.getEmail(), studentDTO);

        incrementCounts(student);

        return student;
    }

    private String dateString(Date dob) {
        if (dob == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(dob);
    }

    private BatchModel getBatch(String name) {
        try {
            BatchModel batch = bRepo.getBatchFromName(name);
            System.out.println("Searching for batch with name: " + name);
            System.out.println("FOr testing" + bRepo.findAll().get(0).getName());
            System.out.println("Got batch: " + batch.getName());
            return batch;
        } catch (Exception e) {
            System.err.println("Error while fetching batch: " + e.getLocalizedMessage());
            return null;
        }
    }

    private DepartmentModel getDept(String department) {
        try {
            return deptRepo.findBySN(department);
        } catch (Exception e) {
            System.err.println("Error while fetching dept: " + e.getLocalizedMessage());
            return null;
        }
    }

    private SpecializationModel getSpecialization(String specialization) {
        try {
            return specRepo.findBySN(specialization);
        } catch (Exception e) {
            System.err.println("Error while fetching batch: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<StudentModel> getAll() {
        return studentRepo.findAll();
    }

    @Override
    public void bulkInsertDepts(MultipartFile file) throws IOException {
        System.out.println("Entered service");

        // Define the expected CSV headers
        String[] headers = {"name", "contact", "email", "regNum", "dept", "spec", "batch", "dob"};

        // Use CsvService to parse the CSV file and map records to StudentModel
        List<StudentModel> students = csvService.parseCsv(file, headers, record -> {
            try {
                // Generate encrypted password for each student

                // Map record fields to StudentModel
                StudentModel student = new StudentModel();
                student.setName(record.get("name"));
                student.setUsername(record.get("regNum"));
                student.setEmail(record.get("email"));
                student.setContact(record.get("contact"));
                student.setProfilePicture(profilePictureUrl);
                student.setRole(UserRole.STUDENT);
                student.setDob(parseDate(record.get("dob")));
                student.setRegNum(record.get("regNum"));
                student.setSpecialization(getSpecialization(record.get("spec")));
                student.setDept(getDept(record.get("dept")));
                student.setBatch(getBatch(record.get("batch")));

                return student;
            } catch (Exception e) {
                System.err.println("Error processing record: " + record + " - " + e.getMessage());
                return null;
            }
        });

        // Remove null entries from the list (skipped rows)
        students.removeIf(student -> student == null);

        // Save valid student records
        saveStudents(students);
    }

    @Override
    public void saveStudents(List<StudentModel> students) {
        for (StudentModel student : students) {
            // Generate a plaintext password for email
            String currPass = passService.generatePassword(8);
            student.setPassword(passwordEncoder.encode(currPass));

            studentRepo.save(student);
            incrementCounts(student);

            StudentMailDTO dto = new StudentMailDTO();
            dto.setEmail(student.getEmail());
            dto.setName(student.getName());
            dto.setPassword(currPass);

            mailService.sendStudentAccountConfirmationMail(student.getEmail(), dto);

            System.out.println("Saved student with ID: " + student.getId());
        }
    }


    public Date parseDate(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return sdf.parse(string);
        } catch (Exception e) {
            System.out.println("Invalid date format: " + string);
            return null;
        }
    }

    @Transactional
    protected void incrementCounts(StudentModel student) {
        // Increment counts for Department, Specialization, and Batch
        deptRepo.incrementStudentCount(student.getDept().getId());
        specRepo.incrementStudentCount(student.getSpecialization().getId());
        bRepo.incrementStudentCount(student.getBatch().getId());
    }

    @Transactional
    protected void decrementCounts(StudentModel student) {
        // Decrement counts for Department, Specialization, and Batch
        deptRepo.decrementStudentCount(student.getDept().getId());
        specRepo.decrementStudentCount(student.getSpecialization().getId());
        bRepo.decrementStudentCount(student.getBatch().getId());
    }

    @Override
    public void deleteStudent(UUID studentId) {
        // Retrieve the student before deletion
        StudentModel student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Decrement counts
        decrementCounts(student);

        // Delete the student
        studentRepo.deleteById(studentId);

        System.out.println("Deleted student with ID: " + studentId);
    }


}