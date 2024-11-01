package com.scrs.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public AdminModel createAdmin(AdminRegsDTO adminDTO, MultipartFile profilePicture) throws IOException {
		// Encrypt the password using BCryptPasswordEncoder
		String encryptedPassword = passwordEncoder.encode(adminDTO.getPassword());

		String profilePictureUrl = null;

		if (profilePicture != null && !profilePicture.isEmpty()) {
			if (profilePicture.getSize() > MAX_SIZE) {
				throw new IllegalArgumentException("Profile picture is too large");
			}

			Map<?, ?> uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
			profilePictureUrl = (String) uploadResult.get("url");
		}

		boolean isSuperAdmin = true; // Set default value for isSuperAdmin, adjust if necessary

		// Create AdminModel entity with the provided DTO data and Cloudinary URL
		AdminModel admin = new AdminModel(adminDTO.getName(), adminDTO.getUsername(), adminDTO.getEmail(),
				encryptedPassword, adminDTO.getContact(), profilePictureUrl, isSuperAdmin);
		System.out.println("SAved admin: " + admin.toString());
		// Save the admin entity to the database and return the saved entity
		return adminRepository.save(admin);
	}

}
