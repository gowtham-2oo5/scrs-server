package com.scrs.dto;

import java.util.Base64;

public class AdminRegsDTO {

	private String name;

	private String username;

	private String email;
	private String password;
	private String contact;

	private String profilePicture; // Now stored as a Base64-encoded String
	boolean isSuperAdmin;

	public AdminRegsDTO() {
		super();
	}

	public AdminRegsDTO(String name, String username, String email, String password, String contact,
			byte[] profilePicture, boolean isSuperAdmin) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.contact = contact;
		setProfilePicture(profilePicture); // Use setter to handle Base64 conversion
		this.isSuperAdmin = isSuperAdmin;
	}

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

	public String getProfilePicture() {
		return profilePicture;
	}

	// Setter accepts byte array and converts to Base64 string
	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = Base64.getEncoder().encodeToString(profilePicture);
	}

	@Override
	public String toString() {
		return "AdminDTO [ name=" + name + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", contact=" + contact + ", profilePicture=" + profilePicture + "]";
	}

}
