package com.scrs.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.scrs.dto.StudentRegsDTO;
import com.scrs.model.BatchModel;
import com.scrs.model.DepartmentModel;
import com.scrs.model.SpecializationModel;
import com.scrs.model.StudentModel;
import com.scrs.model.SemesterEnum;
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
	private PasswordEncoder passwordEncoder; // Change from BCryptPasswordEncoder to PasswordEncoder

	private static final long MAX_SIZE = 5 * 1024 * 1024;

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public StudentModel createStudent(StudentRegsDTO studentDTO, MultipartFile profilePicture) throws IOException {
		// Encrypt the password using BCryptPasswordEncoder
		String encryptedPassword = passwordEncoder.encode(studentDTO.getPassword());

		String profilePictureUrl = null;

		if (profilePicture != null && profilePicture.isEmpty()) {
			if (profilePicture.getSize() > MAX_SIZE) {
				throw new IllegalArgumentException("Profile picture is too large");
			}

			Map<?, ?> uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
			profilePictureUrl = (String) uploadResult.get("url");
			System.out.println(profilePictureUrl);
		}
		System.out.println("Going to create new Student");
		StudentModel student = new StudentModel();

		student.setName(studentDTO.getName());
		student.setUsername(studentDTO.getUsername());
		student.setEmail(studentDTO.getEmail());
		student.setContact(studentDTO.getContact());
		student.setPassword(encryptedPassword);
		student.setUserRole(UserRole.STUDENT);
		student.setProfilePicture(profilePictureUrl);
		student.setRegNum(studentDTO.getRegNum());
		student.setSpecialization(getSpecialization(studentDTO.getSpecialization()));
		student.setDept(getDept(studentDTO.getDepartment()));
		student.setBatch(getBatch(studentDTO.getBatch()));
		student.setJoinedAt(studentDTO.getJoinedAt());

		studentRepo.save(student);

		return student;
	}

	private BatchModel getBatch(String name) {
		return bRepo.getBatchFromName(name);
	}

	private DepartmentModel getDept(String department) {
		return deptRepo.findBySN(department);
	}

	private SpecializationModel getSpecialization(String specialization) {
		return specRepo.findBySN(specialization);
	}
}