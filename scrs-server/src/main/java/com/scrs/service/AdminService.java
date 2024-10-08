package com.scrs.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.AdminDTO;
import com.scrs.model.AdminModel;

public interface AdminService {
	
	public AdminModel createAdmin(AdminDTO adminDTO, MultipartFile profilePicture) throws IOException;

}
