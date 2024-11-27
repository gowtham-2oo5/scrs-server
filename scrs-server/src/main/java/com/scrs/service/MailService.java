package com.scrs.service;

import com.scrs.dto.StudentMailDTO;

public interface MailService {

	public void sendStudentAccountConfirmationMail(String email, StudentMailDTO student);

	void sendLoginOtp(String otp, String mail);

}
