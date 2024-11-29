package com.scrs.dto;

import java.util.Date;

import com.scrs.model.FacultyModel;
import com.scrs.model.UserModel;

public class FacultyDTO {
	private String name;
	private String email;
	private String contact;
	private String profilePicture;
	private Date dob;
	private String empId;
	private String department;
	private String designation;
	private String exp;

	public FacultyDTO(UserModel user, FacultyModel facultyModel) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.contact = user.getContact();
		this.profilePicture = user.getProfilePicture();
		this.dob = user.getDob();
		this.empId = facultyModel.getEmpId();
		this.department = facultyModel.getDepartment().getDeptName();
		this.designation = facultyModel.getDesignation().name();
		this.exp = facultyModel.getExp();
	}

	public FacultyDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

}
