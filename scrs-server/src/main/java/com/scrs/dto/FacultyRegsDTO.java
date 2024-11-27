package com.scrs.dto;

import java.util.Date;

public class FacultyRegsDTO {

	private String name;
	private String contact;
	private String mail;
	private String empId;
	private String dept;
	private Date dob;
	private String designation;
	private Date joined_at;
	private String exp;

	public Date getJoined_at() {
		return joined_at;
	}

	public void setJoined_at(Date joined_at) {
		this.joined_at = joined_at;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}