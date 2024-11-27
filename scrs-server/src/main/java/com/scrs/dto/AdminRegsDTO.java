package com.scrs.dto;

import java.util.Date;

public class AdminRegsDTO {

	private String name;
	private String username;
	private String email;
	private String password;
	private String contact;
	private Date dob;

	public AdminRegsDTO() {
		super();
	}

	public AdminRegsDTO(String name, String username, String email, String password, String contact,
			boolean isSuperAdmin, Date dob) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.dob = dob;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	@Override
	public String toString() {
		return "AdminDTO [ name=" + name + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", contact=" + contact + "]";
	}

}
