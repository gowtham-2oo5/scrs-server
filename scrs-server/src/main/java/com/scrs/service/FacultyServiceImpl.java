package com.scrs.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.FacultyDTO;
import com.scrs.dto.FacultyRegsDTO;
import com.scrs.model.DepartmentModel;
import com.scrs.model.FacultyDesignation;
import com.scrs.model.FacultyModel;
import com.scrs.model.UserRole;
import com.scrs.repository.DepartmentRepo;
import com.scrs.repository.FacultyRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	private FacultyRepo facRepo;

	@Autowired
	private DepartmentRepo deptRepo;

	@Autowired
	private PasswordEncoder passwordEncoder; // Change from BCryptPasswordEncoder to PasswordEncoder

	@SuppressWarnings("unused")
	@Autowired
	private PhotoService photoSer;

	@Autowired
	private CsvService csvService;

	@Override
	public void addFaculty(FacultyRegsDTO fac) {
		try {
			FacultyModel faculty = new FacultyModel();
			facRepo.save(faculty);
			System.out.println("Saved Faculty: " + faculty.toString());
		} catch (Exception e) {
			System.out.println("Error adding faculty: " + e.getLocalizedMessage());
		}
	}

	@Override
	public FacultyModel findByUsername(String uname) {
		try {
			FacultyModel faculty = facRepo.getByUsername(uname);
			System.out.println(faculty.toString());
			return faculty;
		} catch (Exception e) {
			System.out.println("Can't find faculty with username: " + e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public FacultyModel createFaculty(FacultyRegsDTO facDetails, MultipartFile profilePicture) {
		String encryptedPassword = passwordEncoder.encode("test");
		DepartmentModel facDept = getDept(facDetails.getDept());

		if (facDept != null) {
			FacultyModel faculty = new FacultyModel();
			faculty.setName(facDetails.getName());
			faculty.setUsername(facDetails.getEmpId());
			faculty.setEmail(facDetails.getMail());
			faculty.setPassword(encryptedPassword);
			faculty.setContact(facDetails.getContact());
			faculty.setProfilePicture(profilePictureUrl);
			faculty.setUserRole(UserRole.FACULTY);
			faculty.setDesignation(getDesignation(facDetails.getDesignation()));
			faculty.setDepartment(facDept);
			faculty.setEmpId(facDetails.getEmpId());
			faculty.setDob(facDetails.getDob());
			facRepo.save(faculty);
			return faculty;
		} else {
			System.out.println("Error Adding Faculty, Invalid Department");
			return null;
		}
	}

	@Override
	public UUID createSingleFaculty(FacultyRegsDTO facDetails) {
		try {
			String plainPass = parseDateString(facDetails.getDob());
			String encryptedPassword = passwordEncoder.encode(plainPass);
			DepartmentModel facDept = getDept(facDetails.getDept());

			if (facDept != null) {
				FacultyModel faculty = new FacultyModel();
				faculty.setName(facDetails.getName());
				faculty.setUsername(facDetails.getEmpId());
				faculty.setEmail(facDetails.getMail());
				faculty.setPassword(encryptedPassword);
				faculty.setContact(facDetails.getContact());
				faculty.setProfilePicture(profilePictureUrl);
				faculty.setUserRole(UserRole.FACULTY);
				faculty.setDesignation(getDesignation(facDetails.getDesignation()));
				faculty.setDepartment(facDept);
				faculty.setEmpId(facDetails.getEmpId());
				faculty.setDob(facDetails.getDob());
				faculty.setExp(facDetails.getExp());
				faculty.setJoined_at(facDetails.getJoined_at());
				facRepo.save(faculty);
				return faculty.getId();
			} else {
				System.out.println("Error Adding Faculty, Invalid Department");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Error creating single faculty: " + e.getMessage());
			return null;
		}
	}

	@Override
	public void delFac(String uname) {
		try {
			FacultyModel targetFac = facRepo.getByUsername(uname);
			facRepo.delete(targetFac);
		} catch (Exception e) {
			System.err.println("Error deleting faculty: " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<FacultyDTO> getAll() {
		List<FacultyModel> facList = facRepo.findAll();
		List<FacultyDTO> facultyList = new ArrayList<>();

		for (FacultyModel faculty : facList) {
			FacultyDTO fac = mapToDTO(faculty);
			facultyList.add(fac);
		}
		return facultyList;
	}

	@Override
	public List<FacultyDTO> getFacultiesByDept(String sn) {
		System.out.println("Searching SN : " + sn);
		DepartmentModel department = getDept(sn);
		System.out.println("received dept: " + department);
		if (department == null) {
			throw new IllegalArgumentException("Department not found with SN: " + sn);
		}

		List<FacultyModel> facList = facRepo.getFacultiesByDept(department);
		if (facList == null || facList.isEmpty()) {
			return Collections.emptyList();
		}

		List<FacultyDTO> facultyList = new ArrayList<>();
		for (FacultyModel faculty : facList) {
			FacultyDTO fac = mapToDTO(faculty);
			facultyList.add(fac);
		}
		return facultyList;
	}

	@Override
	public void bulkInsertDepts(MultipartFile file) throws IOException {
		String[] headers = { "name", "contact", "email", "empId", "dept", "dob", "designation", "joined_at", "exp" };

		List<FacultyModel> facs = csvService.parseCsv(file, headers, record -> {
			try {
				if (record.size() < headers.length) {
					System.err.println("Skipping malformed row: " + record);
					return null;
				}

				FacultyModel fac = new FacultyModel();
				fac.setName(record.get("name"));
				fac.setUsername(record.get("empId"));
				fac.setPassword(passwordEncoder.encode(parseDateString(parseDate(record.get("dob")))));
				fac.setContact(record.isSet("contact") ? record.get("contact") : null);
				fac.setEmail(record.get("email"));
				fac.setDepartment(getDept(record.get("dept")));
				fac.setEmpId(record.get("empId"));
				fac.setProfilePicture(profilePictureUrl);
				fac.setDob(parseDate(record.get("dob")));
				fac.setDesignation(getDesignation(record.get("designation")));
				fac.setJoined_at(parseDate(record.get("joined_at")));
				fac.setExp(record.get("exp"));
				return fac;
			} catch (Exception e) {
				System.err.println("Error processing record: " + record + " - " + e.getMessage());
				return null;
			}
		});

		facs.removeIf(fac -> fac == null);
		saveFacs(facs);
	}

	private FacultyDTO mapToDTO(FacultyModel faculty) {
		FacultyDTO fac = new FacultyDTO();
		fac.setName(faculty.getName());
		fac.setEmail(faculty.getEmail());
		fac.setContact(faculty.getContact());
		fac.setDepartment(faculty.getDepartment().getDeptName());
		fac.setDesignation(faculty.getDesignation().toString());
		fac.setDob(faculty.getDob());
		fac.setEmpId(faculty.getEmpId());
		fac.setExp(faculty.getExp());
		fac.setProfilePicture(faculty.getProfilePicture());
		return fac;
	}

	private String parseDateString(Date dob) {
		if (dob == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(dob);
	}

	private Date parseDate(String string) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			return sdf.parse(string);
		} catch (Exception e) {
			System.out.println("Invalid date format: " + string);
			return null;
		}
	}

	private void saveFacs(List<FacultyModel> facs) {
		for (FacultyModel fac : facs) {
			facRepo.save(fac);
			System.out.println("Saved faculty with empId: " + fac.getEmpId() + " , id: " + fac.getId());
		}
	}

	private DepartmentModel getDept(String dept) {
		return deptRepo.findBySN(dept);
	}

	private FacultyDesignation getDesignation(String designation) {
		try {
			return FacultyDesignation.valueOf(designation);
		} catch (IllegalArgumentException | NullPointerException e) {
			return FacultyDesignation.PROFESSOR;
		}
	}

	@Override
	public FacultyModel getFacById(String hodId) {
	    System.out.println("Retrieving faculty details for ID: " + hodId);

	    if (hodId == null || hodId.isEmpty()) {
	        throw new IllegalArgumentException("HOD ID cannot be null or empty.");
	    }

	    FacultyModel faculty = facRepo.findByEmpId(hodId);
	    if (faculty == null) {
	        System.out.println("Faculty not found with ID: " + hodId);
	        throw new EntityNotFoundException("Faculty with ID " + hodId + " not found.");
	    }

	    System.out.println("Successfully retrieved faculty details for ID: " + hodId);
	    return faculty;
	}

}
