package com.scrs.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scrs.dto.AdminDTO;
import com.scrs.dto.AuthResponse;
import com.scrs.dto.FacultyDTO;
import com.scrs.dto.StudentDTO;
import com.scrs.model.AdminModel;
import com.scrs.model.FacultyModel;
import com.scrs.model.StudentModel;
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
	private JwtService jwtService;

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
		if (isOtpInvalid(otp)) {
			return "Invalid or Expired OTP";
		}

		UserModel user = userRepo.findByUsername(username);
		generatedOtp = null;
		otpGeneratedTime = null;

		AuthResponse response = createAuthResponseForUser(user);
		if (response != null) {
			String token = jwtService.generateToken(response);
			response.setToken(token);
		}
		return response.getToken();
	}

	private boolean isOtpInvalid(String otp) {
		long currentTime = System.currentTimeMillis();
		return generatedOtp == null || !generatedOtp.equals(otp) || (currentTime - otpGeneratedTime >= otpValidity);
	}

	private AuthResponse createAuthResponseForUser(UserModel user) {
		UserRole userRole = user.getUserRole();
		AuthResponse response = new AuthResponse();

		switch (userRole) {
		case ADMIN:
			return buildAdminResponse(user, response);
		case FACULTY:
			return buildFacultyResponse(user, response);
		case STUDENT:
			return buildStudentResponse(user, response);
		default:
			throw new IllegalArgumentException("Invalid user role: " + userRole);
		}
	}

	private AuthResponse buildAdminResponse(UserModel user, AuthResponse response) {
		AdminModel adminModel = (AdminModel) user;
		AdminDTO adminDTO = new AdminDTO(user, adminModel.isSuperAdmin());
		response.setData(adminDTO);
		response.setUsername(adminModel.getUsername());
		response.setRole(adminModel.getUserRole().name());
		return response;
	}

	private AuthResponse buildFacultyResponse(UserModel user, AuthResponse response) {
		FacultyModel facultyModel = (FacultyModel) user;
		FacultyDTO facultyDTO = new FacultyDTO(user, facultyModel);
		response.setData(facultyDTO);
		response.setUsername(facultyModel.getUsername());
		response.setRole(facultyModel.getUserRole().name());
		return response;
	}

	private AuthResponse buildStudentResponse(UserModel user, AuthResponse response) {
		StudentModel studentModel = (StudentModel) user;
		StudentDTO studentDTO = new StudentDTO(user, studentModel.getRegNum());
		response.setData(studentDTO);
		response.setUsername(studentModel.getUsername());
		response.setRole(studentDTO.getRole().name());
		return response;
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
