package com.scrs.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scrs.dto.AdminDTO;
import com.scrs.model.AdminModel;
import com.scrs.model.UserModel;
import com.scrs.model.UserRole;
import com.scrs.repository.UserRepo;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	private String username;

	private String generatedOtp;
	private Integer otpValidity;
	private Long otpGeneratedTime;

	public AuthServiceImpl() {
		otpValidity = 300000;
	}

	@Override
	public String authenticate(String username, String password) {
		this.username = username;
		System.out.println("Checking uname: " + username);
		UserModel user = userRepo.findByUsername(username);
		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				String maskedEmail = maskEmail(user.getEmail());
				return sendOtp(user.getEmail(), maskedEmail);
			} else {
				return "Invalid Credentials";
			}
		} else {
			return "User doesn't exist";
		}
	}

	public String maskEmail(String email) {
		int atSymbolIndex = email.indexOf('@');
		if (atSymbolIndex <= 2) {
			return email;
		}
		return email.substring(0, 2) + "****" + email.substring(atSymbolIndex - 2);
	}

	@Override
	public Object verifyOtp(String otp) {
		long currentTime = System.currentTimeMillis();

		if (generatedOtp != null && generatedOtp.equals(otp) && (currentTime - otpGeneratedTime < otpValidity)) {
			System.out.println("OTP Verified");
			UserModel user = userRepo.findByUsername(username);

			generatedOtp = null;
			otpGeneratedTime = null;

			UserRole userRole = user.getUserRole();

			// TODO: Modify these return statements for jwt support
			switch (userRole) {
			case ADMIN:
				AdminModel adminModel = (AdminModel) user;
				AdminDTO admin = new AdminDTO(user, adminModel.isSuperAdmin());
				return admin;

			case FACULTY:
				return null;

			case STUDENT:

				return null;

			default:
				throw new IllegalArgumentException("Invalid user role: " + userRole);
			}
		} else {
			return "Invalid or Expired OTP";
		}
	}

	@Override
	public String sendOtp(String mail, String maskedMail) {
		long currentTime = System.currentTimeMillis();

		if (generatedOtp != null && (currentTime - otpGeneratedTime < otpValidity)) {
			return "OTP already sent to: " + maskedMail + ". Please wait for it to expire.";
		}

		SecureRandom secureRandom = new SecureRandom();
		generatedOtp = String.valueOf(secureRandom.nextInt(899999) + 100000); // Generate 6-digit OTP
		otpGeneratedTime = currentTime;

		mailService.sendLoginOtp(generatedOtp, mail);

		return "OTP has been sent to: " + maskedMail;
	}

	public boolean isExpired() {
		long currentTime = System.currentTimeMillis();
		if (generatedOtp == null || (currentTime - otpGeneratedTime > otpValidity)) {
			generatedOtp = null;
			otpGeneratedTime = null;
			return true;
		}
		return false;
	}

}
