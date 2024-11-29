package com.scrs.service;

import java.security.SecureRandom;

public interface PasswordService {

	final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final String DIGITS = "0123456789";
	final String SPECIAL_CHARACTERS = "@$!%*?&";
	final String ALL_ALLOWED = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;
	
	public String generatePassword(int length);
	String shuffleString(String input, SecureRandom random);

}
