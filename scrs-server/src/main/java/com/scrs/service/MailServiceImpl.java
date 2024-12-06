package com.scrs.service;

import com.scrs.dto.StudentMailDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Value("${client.url}")
    String clientUrl;

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

            // mailSender.send(message);

            System.out.println("OTP sent to: " + mail);
            System.out.println("OTP is: " + otp);

        } catch (Exception e) {
            System.err.println("Failed to send OTP: " + e.getMessage());
        }
    }

    @Override
    public void sendStudentAccountConfirmationMail(String email, StudentMailDTO student) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Welcome to Course Registration Portal");

            String emailContent = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "  <style>" +
                    "    body { font-family: 'Arial', sans-serif; color: #333; line-height: 1.6; background-color: #f4f4f4; }" +
                    "    .email-container { max-width: 600px; margin: 20px auto; background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }" +
                    "    .header { background-color: #2a9df4; color: white; padding: 15px; text-align: center; border-radius: 8px 8px 0 0; }" +
                    "    .content { padding: 20px; text-align: center; }" +
                    "    .credentials { background-color: #f9f9f9; padding: 15px; border-radius: 8px; margin: 20px 0; }" +
                    "    .cta-button { display: inline-block; background-color: #2a9df4; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin-top: 20px; }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <div class='header'>" +
                    "      <h1>Course Registration Portal</h1>" +
                    "    </div>" +
                    "    <div class='content'>" +
                    "      <h2>Welcome, " + student.getName() + "!</h2>" +
                    "      <p>Your account has been successfully created in the Course Registration Portal.</p>" +
                    "      <div class='credentials'>" +
                    "        <p><strong>Email:</strong> " + student.getEmail() + "</p>" +
                    "        <p>Please use the following credentials to log in:</p>" +
                    "        <p><strong>Temporary Password:</strong> " + student.getPassword() + "</p>" +
                    "        <p><em>We recommend changing your password after first login.</em></p>" +
                    "      </div>" +
                    "      <a href='" + clientUrl + "' class='cta-button'>Go to Portal</a>" +
                    "    </div>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);

            mailSender.send(message);

            System.out.println("Confirmation email sent to: " + email);

        } catch (Exception e) {
            System.err.println("Failed to send confirmation email: " + e.getMessage());
        }
    }

}
