package com.scrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.scrs.dto.StudentMailDTO;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendLoginOtp(String otp, String mail) {
		try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(mail);
            helper.setSubject("Your OTP Code");

            String emailContent = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "  <style>" +
                    "    body { font-family: Arial, sans-serif; color: #333; }" +
                    "    .otp-container { background-color: #f4f4f4; padding: 20px; text-align: center; border-radius: 8px; width: 300px; margin: 0 auto; border: 1px solid #ddd; }" +
                    "    .otp-code { font-size: 24px; font-weight: bold; color: #2a9df4; margin-top: 10px; }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='otp-container'>" +
                    "    <p>Your OTP code is:</p>" +
                    "    <p class='otp-code'>" + otp + "</p>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);

            mailSender.send(message);

            System.out.println("OTP sent to: " + mail);
            System.out.println("OTP is: " + otp);

        } catch (Exception e) {
            System.err.println("Failed to send OTP: " + e.getMessage());
        }
	}

	@Override
	public void sendStudentAccountConfirmationMail(String email, StudentMailDTO student) {
		// TODO Auto-generated method stub

	}

}
