package com.scrs.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.StudentRegsDTO;
import com.scrs.model.StudentModel;

public interface StudentService {

	public StudentModel createStudent(StudentRegsDTO studentDTO, MultipartFile profilePicture) throws IOException;

}
