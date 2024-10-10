package com.scrs.service;

import java.io.IOException;

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
	private PasswordEncoder passwordEncoder; // Change from BCryptPasswordEncoder to PasswordEncoder

	private static final long MAX_SIZE = 5 * 1024 * 1024;

	@Override
	public AdminModel createAdmin(AdminRegsDTO adminDTO, MultipartFile profilePicture) throws IOException {
		// Encrypt the password using BCryptPasswordEncoder
		String encryptedPassword = passwordEncoder.encode(adminDTO.getPassword());

		// Convert profile picture to byte[] (if it's provided)
		byte[] profilePictureBytes = null;
		if (profilePicture != null) {
			if (profilePicture.getSize() > MAX_SIZE) {
				throw new IllegalArgumentException("Profile picture is too large");
			}
			profilePictureBytes = profilePicture.getBytes();
		}

		boolean isSuperAdmin = true; // Set default value for isSuperAdmin, adjust if necessary

		// Create AdminModel entity with the provided DTO data
		AdminModel admin = new AdminModel(adminDTO.getName(), adminDTO.getUsername(), adminDTO.getEmail(),
				encryptedPassword, adminDTO.getContact(), profilePictureBytes, isSuperAdmin);

		// Save the admin entity to the database and return the saved entity
		return adminRepository.save(admin);
	}

}
