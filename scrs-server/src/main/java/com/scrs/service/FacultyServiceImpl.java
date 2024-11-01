package com.scrs.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.scrs.dto.FacultyRegsDTO;
import com.scrs.model.DepartmentModel;
import com.scrs.model.FacultyDesignation;
import com.scrs.model.FacultyModel;
import com.scrs.model.UserRole;
import com.scrs.repository.DepartmentRepo;
import com.scrs.repository.FacultyRepo;

@Service
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	private FacultyRepo facRepo;

	@Autowired
	private DepartmentRepo deptRepo;

	@Autowired
	private PasswordEncoder passwordEncoder; // Change from BCryptPasswordEncoder to PasswordEncoder

	private static final long MAX_SIZE = 5 * 1024 * 1024;

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public void addFaculty(FacultyRegsDTO fac) {
		try {
			FacultyModel faculty = new FacultyModel();

			faculty.setName(fac.getName());
			faculty.setUsername(fac.getUsername());
			faculty.setContact(fac.getContact());
			faculty.setDepartment(getDept(fac.getDept()));
			faculty.setEmail(fac.getMail());
			faculty.setEmpId(fac.getEmpId());
			faculty.setPassword(fac.getPass());

			facRepo.save(faculty);
			System.out.println("Saved Faculty: " + faculty.toString());
		} catch (Exception e) {
			System.out.println("Error adding faculty: " + e.getLocalizedMessage());
		}
	}

	private DepartmentModel getDept(String dept) {
		return deptRepo.findBySN(dept);
	}

	@Override
	public FacultyModel findByUsername(String uname) {
		FacultyModel faculty = null;
		try {
			System.out.println(facRepo.getByUsername(uname).toString());
			faculty = facRepo.getByUsername(uname);
		} catch (Exception e) {
			System.out.println("Cant find faculty with username, " + e.getLocalizedMessage());
		}
		return faculty;

	}

	@Override
	public FacultyModel createFaculty(FacultyRegsDTO facDetails, MultipartFile profilePicture) {
		String encryptedPassword = passwordEncoder.encode(facDetails.getPass());

		String profilePictureUrl = null;

		if (profilePicture != null && !profilePicture.isEmpty()) {
			if (profilePicture.getSize() > MAX_SIZE) {
				throw new IllegalArgumentException("Profile picture is too large");
			}

			Map<?, ?> uploadResult = null;
			try {
				uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			profilePictureUrl = (String) uploadResult.get("url");
		}

		DepartmentModel facDept = deptRepo.findBySN(facDetails.getDept());

		FacultyModel faculty = new FacultyModel();
		if (!isNull(facDept)) {
			faculty.setName(facDetails.getName());
			faculty.setUsername(facDetails.getUsername());
			faculty.setEmail(facDetails.getMail());
			faculty.setPassword(encryptedPassword);
			faculty.setContact(facDetails.getContact());
			faculty.setProfilePicture(profilePictureUrl);
			faculty.setUserRole(UserRole.FACULTY);
			faculty.setDesignation(getDesignation(facDetails.getDesignation()));
			faculty.setDepartment(facDept);
			faculty.setEmpId(facDetails.getEmpId());
			faculty.setJoinedAt(facDetails.getJoiningData());
			facRepo.save(faculty);
		} else {
			System.out.println("Error Adding Faculty, Invalid Dept");
		}
		return faculty;
	}

	private FacultyDesignation getDesignation(String designation) {
		try {
			return FacultyDesignation.valueOf(designation);
		} catch (IllegalArgumentException | NullPointerException e) {
			return FacultyDesignation.PROFESSOR;
		}
	}

	private boolean isNull(DepartmentModel facDept) {
		return facDept == null;
	}

	@Override
	public void delFac(String uname) {
		FacultyModel targetFac = facRepo.getByUsername(uname);
		facRepo.delete(targetFac);
	}

}
