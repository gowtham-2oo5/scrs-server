package com.scrs.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.StudentRegsDTO;
import com.scrs.model.BatchModel;
import com.scrs.model.DepartmentModel;
import com.scrs.model.SpecializationModel;
import com.scrs.model.StudentModel;
import com.scrs.model.UserRole;
import com.scrs.repository.BatchRepo;
import com.scrs.repository.DepartmentRepo;
import com.scrs.repository.SpecializationRepo;
import com.scrs.repository.StudentRepo;

import jakarta.transaction.Transactional;

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

	@Override
	public StudentModel createStudent(StudentRegsDTO dto, MultipartFile profilePicture) throws IOException {
		String encryptedPassword = passwordEncoder.encode(dateString(dto.getDob()));
		System.out.println("Is null at service ?" + profilePicture == null);

		System.out.println("Going to create new Student");
		StudentModel student = new StudentModel();

		student.setName(dto.getName());
		student.setEmail(dto.getEmail());
		student.setContact(dto.getContact());
		student.setUsername(dto.getRegNum());
		student.setPassword(encryptedPassword);
		student.setUserRole(UserRole.STUDENT);
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

		System.out.println("Got Student: " + student.toString());

		// Save student and increment counts
		studentRepo.save(student);
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
		String[] headers = { "name", "contact", "email", "regNum", "dept", "spec", "batch", "dob" };

		// Use CsvService to parse the CSV file and map records to StudentModel
		List<StudentModel> students = csvService.parseCsv(file, headers, record -> {
			try {
				// Generate encrypted password for each student
				String encryptedPassword = passwordEncoder.encode(passService.generatePassword(8));

				// Map record fields to StudentModel
				StudentModel student = new StudentModel();
				student.setName(record.get("name"));
				student.setUsername(record.get("regNum"));
				student.setEmail(record.get("email"));
				student.setPassword(encryptedPassword);
				student.setContact(record.get("contact"));
				student.setProfilePicture(profilePictureUrl);
				student.setUserRole(UserRole.STUDENT);
				student.setDob(parseDate(record.get("dob")));
				student.setRegNum(record.get("regNum"));
				student.setSpecialization(getSpecialization(record.get("spec")));
				student.setDept(getDept(record.get("dept")));
				student.setBatch(getBatch(record.get("batch")));

				return student;
			} catch (Exception e) {
				System.err.println("Error processing record: " + record + " - " + e.getMessage());
				return null; // Skip records that cause exceptions
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
	        studentRepo.save(student);
	        incrementCounts(student); // Increment counts for each saved student
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
	private void incrementCounts(StudentModel student) {
		// Increment counts for Department, Specialization, and Batch
		deptRepo.incrementStudentCount(student.getDept().getId());
		specRepo.incrementStudentCount(student.getSpecialization().getId());
		bRepo.incrementStudentCount(student.getBatch().getId());
	}

	@Transactional
	private void decrementCounts(StudentModel student) {
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