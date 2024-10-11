package com.scrs.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculty")
@DiscriminatorValue("fac")
public class FacultyModel extends UserModel {

	private String empId;
	private String department;
	private String joinedAt;
	private int experience;

	@Column(name = "instructing_course")
	private String instructingCourse;

	@Enumerated(EnumType.STRING)
	private FacultyDesignation designation;

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

	public String getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(String joinedAt) {
		this.joinedAt = joinedAt;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getInstructingCourse() {
		return instructingCourse;
	}

	public FacultyDesignation getDesignation() {
		return designation;
	}

	public void setDesignation(FacultyDesignation designation) {
		this.designation = designation;
	}

	public void setInstructingCourse(String instructingCourse) {
		this.instructingCourse = instructingCourse;
	}

}
