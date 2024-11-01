package com.scrs.dto;

import java.util.Date;

public class StudentRegsDTO {

	private String name;
	private String username;
	private String email;
	private String password;
	private String contact;
	private String regNum;
	private String specialization;
	private String department;
	private String batch;
	private Date joinedAt;

	public StudentRegsDTO(String name, String username, String email, String password, String contact, String regNum,
			String specialization, String department, String batch, Date joinedAt) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.regNum = regNum;
		this.specialization = specialization;
		this.department = department;
		this.batch = batch;
		this.joinedAt = joinedAt;
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

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public StudentRegsDTO() {
		super();
	}

	public Date getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
	}

}
