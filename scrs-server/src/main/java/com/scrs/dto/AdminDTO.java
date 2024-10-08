package com.scrs.dto;

import java.util.Arrays;

public class AdminDTO {

	private String name;

	private String username;

	private String email;
	private String password;
	private String contact;

	private byte[] profilePicture;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public String toString() {
		return "AdminDTO [ name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", contact=" + contact + ", profilePicture=" + Arrays.toString(profilePicture) + "]";
	}

}
