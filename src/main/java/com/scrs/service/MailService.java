package com.scrs.service;

import com.scrs.dto.AccountConfirmationMailDTO;

public interface MailService {


    void sendStudentAccountConfirmationMail(String email, AccountConfirmationMailDTO student);

    void sendLoginOtp(String otp, String mail);

}
