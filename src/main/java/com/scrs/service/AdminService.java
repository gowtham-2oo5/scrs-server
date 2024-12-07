package com.scrs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.AdminRegsDTO;
import com.scrs.model.AdminModel;

public interface AdminService {

	public AdminModel createAdmin(AdminRegsDTO adminDetails, MultipartFile profilePicture, Boolean isSuperAdmin)
			throws IOException;

	public List<AdminModel> getAllAdmins();

}
