package com.scrs.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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

	@Override
	public StudentModel createStudent(StudentRegsDTO dto, MultipartFile profilePicture) throws IOException {
		String encryptedPassword = passwordEncoder.encode(dateString(dto.getDob()));
		System.out.println("Is null at service ?" + profilePicture.isEmpty());
		// photoService.savePic(profilePicture);

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
		student.setContact(dto.getContact());
		student.setDob(dto.getDob());
		SpecializationModel spec = getSpecialization(dto.getSpec());
		if (spec != null)
			student.setSpecialization(getSpecialization(dto.getSpec()));
		else {
			System.err.println("NULL Specialization");
			return null;
		}
		DepartmentModel dept = getDept(dto.getDept());
		if (dept != null)
			student.setDept(dept);
		else {
			System.err.println("NULL Dept");
			return null;
		}
		BatchModel batch = getBatch(dto.getBatch());
		if (batch != null)
			student.setBatch(batch);
		else {
			System.err.println("NULL Batch");
			return null;
		}
		student.setDob(dto.getDob());
		/*
		 * student.setName(studentDTO.getName());
		 * student.setUsername(studentDTO.getUsername());
		 * student.setEmail(studentDTO.getEmail());
		 * student.setContact(studentDTO.getContact());
		 * student.setPassword(encryptedPassword);
		 * student.setUserRole(UserRole.STUDENT);
		 * student.setProfilePicture(profilePictureUrl);
		 * student.setRegNum(studentDTO.getRegNum());
		 * student.setSpecialization(getSpecialization(studentDTO.getSpecialization()));
		 * student.setDept(getDept(studentDTO.getDepartment()));
		 * student.setBatch(getBatch(studentDTO.getYear(), studentDTO.getSemester()));
		 * student.setJoinedAt(studentDTO.getJoinedAt());
		 */
		// studentRepo.save(student);
		System.out.println("Got Student: " + student.toString());
		studentRepo.save(student);
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
			return bRepo.getBatchFromName(name);
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
	public void bulkInsertDepts(MultipartFile file) throws IOException{
		
		System.out.println("Entered service");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.EXCEL.builder()
						.setHeader("name","contact","email","regNum","dept","spec","batch","dob")
						.setSkipHeaderRecord(true).build())) {
			List<StudentModel> students = new ArrayList<>();
			for (CSVRecord record : csvParser) {
				String encryptedPassword = passwordEncoder.encode(parseDateString(parseDate(record.get("dob"))));

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
				
				students.add(student);
			}
			saveStudents(students);
		}

	}

	private void saveStudents(List<StudentModel> students) {
		for(StudentModel student : students) {
			studentRepo.save(student);
			System.out.println("Saving student with ID: "+ student.getId());
		}
	}

	private String parseDateString(Date dob) {
		if (dob == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(dob);
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
}