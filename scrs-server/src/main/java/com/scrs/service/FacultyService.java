package com.scrs.service;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.FacultyRegsDTO;
import com.scrs.model.FacultyModel;

public interface FacultyService {

	public void addFaculty(FacultyRegsDTO fac);
	
	public FacultyModel findByUsername(String uname);

	public FacultyModel createFaculty(FacultyRegsDTO facDetails, MultipartFile profilePicture);

	public void delFac(String uname);

}