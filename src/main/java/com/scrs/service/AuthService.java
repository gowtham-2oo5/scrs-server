package com.scrs.service;

public interface AuthService {

	public String authenticate(String username, String password);

	public Object verifyOtp(String otp);

	String sendOtp(String mail, String maskedMail);

}
