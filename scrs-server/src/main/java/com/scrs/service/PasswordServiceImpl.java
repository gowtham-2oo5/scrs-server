package com.scrs.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

	@Override
	public String generatePassword(int length) {
		if (length < 8) {
			throw new IllegalArgumentException("Password length must be at least 8 characters.");
		}

		SecureRandom random = new SecureRandom();

		StringBuilder password = new StringBuilder();

		// Ensure at least one character from each required category
		password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
		password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
		password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
		password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

		// Fill the rest of the password with random characters from all allowed
		// characters
		for (int i = 4; i < length; i++) {
			password.append(ALL_ALLOWED.charAt(random.nextInt(ALL_ALLOWED.length())));
		}

		// Shuffle the characters to avoid predictable patterns
		return shuffleString(password.toString(), random);
	}

	@Override
	public String shuffleString(String input, SecureRandom random) {
		char[] characters = input.toCharArray();
		for (int i = characters.length - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);
			char temp = characters[i];
			characters[i] = characters[index];
			characters[index] = temp;
		}
		return new String(characters);
	}

}
