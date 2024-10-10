package com.scrs.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.AdminRegsDTO;
import com.scrs.model.AdminModel;

public interface AdminService {
	
	public AdminModel createAdmin(AdminRegsDTO adminDTO, MultipartFile profilePicture) throws IOException;

}
