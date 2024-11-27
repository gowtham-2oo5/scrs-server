package com.scrs.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		String encryptedPassword = passwordEncoder.encode("test");

		DepartmentModel facDept = getDept(facDetails.getDept());

		FacultyModel faculty = new FacultyModel();
		if (!facDept.isEmpty()) {
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

	@Override
	public void delFac(String uname) {
		try {
			FacultyModel targetFac = facRepo.getByUsername(uname);
			facRepo.delete(targetFac);
		} catch (Exception e) {
			System.err.println("error deleting faculty, " + e.getLocalizedMessage());
		}
	}

	@Override
	public Page<FacultyDTO> getAll(Pageable pageable) {

	    System.out.println("Requested Page: " + pageable.getPageNumber());
	    System.out.println("Requested Size: " + pageable.getPageSize());
	    Page<FacultyModel> facultyPage = facRepo.findAll(pageable);
	    System.out.println("Total Elements: " + facultyPage.getTotalElements());
	    System.out.println("Total Pages: " + facultyPage.getTotalPages());
	    System.out.println("Current Page Content: " + facultyPage.getContent());

	    // Map FacultyModel to FacultyDTO
	    Page<FacultyDTO> facultyDTOPage = facultyPage.map(faculty -> {
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
	    });

	    return facultyDTOPage;
	}


	@Override
	public UUID createSingleFaculty(FacultyRegsDTO facDetails) {
		System.out.println("Entered create s fac");
		String plainPass = parseDateString(facDetails.getDob());
		System.out.println("Plain Fac Pass: " + plainPass);
		String encryptedPassword = passwordEncoder.encode(plainPass);

		DepartmentModel facDept = getDept(facDetails.getDept());

		FacultyModel faculty = new FacultyModel();
		if (facDept != null) {
			System.out.println("Creating fac");
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

			System.out.println("DOB of faculty: " + faculty.getDob());
		} else {
			System.out.println("Error Adding Faculty, Invalid Dept");
		}
		return faculty.getId();
	}

	@Override
	public void bulkInsertDepts(MultipartFile file) throws IOException {
		System.out.println("Entered service");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.EXCEL.builder().setHeader("name", "contact", "email", "empId", "dept", "dob",
								"designation", "joined_at", "exp").setSkipHeaderRecord(true).build())) {
			List<FacultyModel> facs = new ArrayList<>();
			for (CSVRecord record : csvParser) {
				if (record.size() < 9) {
					System.err.println("Skipping malformed row: " + record);
					continue; // Skip invalid rows
				}
				String encryptedPassword = passwordEncoder.encode(parseDateString(parseDate(record.get("dob"))));

				FacultyModel fac = new FacultyModel();
				fac.setName(record.get("name"));
				fac.setUsername(record.get("empId"));
				fac.setPassword(encryptedPassword);
				fac.setContact(record.isSet("contact") ? record.get("contact") : null);
				fac.setEmail(record.get("email"));
				fac.setDepartment(getDept(record.get("dept")));
				fac.setEmpId(record.get("empId"));
				fac.setProfilePicture(profilePictureUrl);
				fac.setDob(parseDate(record.get("dob")));
				fac.setDesignation(getDesignation(record.get("designation")));
				fac.setJoined_at(parseDate(record.get("joined_at")));
				fac.setExp(record.get("exp"));
				facs.add(fac);
			}
			saveFacs(facs);
		}

	}

	private String parseDateString(Date dob) {
		if (dob == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(dob);
	}

	private void saveFacs(List<FacultyModel> facs) {
		for (FacultyModel fac : facs) {
			facRepo.save(fac);
			System.out.println("Saved fac of eid: " + fac.getEmpId() + " , id: " + fac.getId());
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

}
