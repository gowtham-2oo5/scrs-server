package com.scrs.dto;

import java.util.Date;

public class FacultyRegsDTO {

	private String name;
	private String username;
	private String contact;
	private String pass;
	private String mail;
	private String empId;
	private String dept;
	private Date joiningDate;
	private String designation;

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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

	public Date getJoiningData() {
		return joiningDate;
	}

	public void setJoiningData(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	@Override
	public String toString() {
		return "FacultyRegsDTO [name=" + name + ", username=" + username + ", contact=" + contact + ", pass=" + pass
				+ ", mail=" + mail + ", empId=" + empId + ", dept=" + dept + ", joiningDate=" + joiningDate
				+ ", designation=" + designation + "]";
	}

}