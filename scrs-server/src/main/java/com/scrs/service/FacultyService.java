package com.scrs.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.FacultyDTO;
import com.scrs.dto.FacultyRegsDTO;
import com.scrs.model.FacultyModel;

public interface FacultyService {

	final String profilePictureUrl = "http://res.cloudinary.com/scrs-user-profile-pictures/image/upload/v1732555250/ay8jqjqva1rlhsqpdhmb.png";

	public void addFaculty(FacultyRegsDTO fac);

	public FacultyModel findByUsername(String uname);

	public FacultyModel createFaculty(FacultyRegsDTO facDetails, MultipartFile profilePicture);

	public void delFac(String uname);

	public Page<FacultyDTO> getAll(Pageable pageRequest);

	public UUID createSingleFaculty(FacultyRegsDTO facDTO);

	public void bulkInsertDepts(MultipartFile file) throws IOException;

}