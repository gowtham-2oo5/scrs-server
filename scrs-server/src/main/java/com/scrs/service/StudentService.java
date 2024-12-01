package com.scrs.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.StudentRegsDTO;
import com.scrs.model.StudentModel;

public interface StudentService {
	

	final String profilePictureUrl = "http://res.cloudinary.com/scrs-user-profile-pictures/image/upload/v1732555250/ay8jqjqva1rlhsqpdhmb.png";

	public StudentModel createStudent(StudentRegsDTO studentDTO, MultipartFile profilePicture) throws IOException;

	public List<StudentModel> getAll();

	public void bulkInsertDepts(MultipartFile file) throws IOException;
	
	public void deleteStudent(UUID studentId);

}
