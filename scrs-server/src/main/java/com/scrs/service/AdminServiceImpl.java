package com.scrs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.AdminRegsDTO;
import com.scrs.model.AdminModel;
import com.scrs.repository.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepo adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PhotoService photoSer;

	@Override
	public AdminModel createAdmin(AdminRegsDTO adminDTO, MultipartFile profilePicture, Boolean isSuperAdmin)
			throws IOException {
		String encryptedPassword = passwordEncoder.encode(adminDTO.getPassword());

		String profilePictureUrl = photoSer.savePic(profilePicture);
		if (profilePicture.isEmpty())
			return null;

		AdminModel admin = new AdminModel(adminDTO.getName(), adminDTO.getUsername(), adminDTO.getEmail(),
				encryptedPassword, adminDTO.getContact(), profilePictureUrl, isSuperAdmin, adminDTO.getDob());
		System.out.println("Saved admin: " + admin.toString());
		return adminRepository.save(admin);
	}

	@Override
	public List<AdminModel> getAllAdmins() {
		try {
			return adminRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}

}
