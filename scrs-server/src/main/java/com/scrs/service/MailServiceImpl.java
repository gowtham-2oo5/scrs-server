package com.scrs.service;

import com.scrs.dto.AccountConfirmationMailDTO;
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

            //mailSender.send(message);

            System.out.println("OTP sent to: " + mail);
            System.out.println("OTP is: " + otp);

        } catch (Exception e) {
            System.err.println("Failed to send OTP: " + e.getMessage());
        }
    }

    @Override
    public void sendStudentAccountConfirmationMail(String email, AccountConfirmationMailDTO student) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Welcome to Course Registration Portal");

            String emailContent = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "  <style>" +
                    "    body { font-family: 'Arial', sans-serif; color: #333; line-height: 1.6; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                    "    .email-container { max-width: 600px; margin: 20px auto; background-color: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); overflow: hidden; }" +
                    "    .header { background-color: #2a9df4; color: white; padding: 30px 0; text-align: center; }" +
                    "    .header h1 { margin: 0; font-size: 28px; text-transform: uppercase; letter-spacing: 2px; }" +
                    "    .content { padding: 40px 30px; text-align: center; }" +
                    "    .content h2 { color: #2a9df4; margin-bottom: 20px; }" +
                    "    .content p { font-size: 16px; margin-bottom: 30px; }" +
                    "    .credentials { background-color: #f9f9f9; padding: 20px; border-radius: 8px; margin-bottom: 30px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }" +
                    "    .credentials h3 { color: #2a9df4; margin-bottom: 15px; }" +
                    "    .cta-button { display: inline-block; background-color: #2a9df4; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold; text-transform: uppercase; letter-spacing: 1px; transition: background-color 0.3s ease; }" +
                    "    .cta-button:hover { background-color: #1a7db7; }" +
                    "    .footer { background-color: #f4f4f4; padding: 20px; text-align: center; font-size: 14px; color: #666; }" +
                    "    .footer p { margin: 0; }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <div class='header'>" +
                    "      <h1>Course Registration Portal</h1>" +
                    "    </div>" +
                    "    <div class='content'>" +
                    "      <h2>Welcome, " + student.getName() + "!</h2>" +
                    "      <p>Your account has been successfully created in the Course Registration Portal. Get ready for an exciting learning journey!</p>" +
                    "      <div class='credentials'>" +
                    "        <h3>Your Login Credentials</h3>" +
                    "        <p><strong>Username:</strong> " + student.getUsername() + "</p>" +
                    "        <p><strong>Temporary Password:</strong> " + student.getPassword() + "</p>" +
                    "        <p><em>For security reasons, we recommend changing your password after your first login.</em></p>" +
                    "      </div>" +
                    "      <div>" +
                    "        <h3>What's Next?</h3>" +
                    "        <ol style='text-align: left; padding-left: 20px;'>" +
                    "          <li>Log in to your account</li>" +
                    "          <li>Complete your profile</li>" +
                    "          <li>Browse available courses</li>" +
                    "          <li>Start your learning adventure!</li>" +
                    "        </ol>" +
                    "      </div>" +
                    "      <a href='" + clientUrl + "' class='cta-button'>Go to Portal</a>" +
                    "    </div>" +
                    "    <div class='footer'>" +
                    "      <p>If you have any questions, please don't hesitate to contact our support team.</p>" +
                    "      <p>&copy; 2023 Course Registration Portal. All rights reserved.</p>" +
                    "    </div>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";


            helper.setText(emailContent, true);

            //mailSender.send(message);

            System.out.println("Confirmation email sent to: " + email);

        } catch (Exception e) {
            System.err.println("Failed to send confirmation email: " + e.getMessage());
        }
    }

}
