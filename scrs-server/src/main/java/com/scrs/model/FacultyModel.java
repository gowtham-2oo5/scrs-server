package com.scrs.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "scrs_faculty")
@DiscriminatorValue("ROLE_FACULTY")
public class FacultyModel extends UserModel {

	@Column(unique = true)
	private String empId;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	@JsonIgnore
	private DepartmentModel department;

	@Column(name = "instructing_course")
	@JsonIgnore
	private String instructingCourse;

	@Enumerated(EnumType.STRING)
	private FacultyDesignation designation;

	@OneToMany(mappedBy = "incharge", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CourseModel> courses;

	private Date joined_at;
	private String exp;

	public FacultyModel() {
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public DepartmentModel getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentModel department) {
		this.department = department;
	}

	public String getInstructingCourse() {
		return instructingCourse;
	}

	public void setInstructingCourse(String instructingCourse) {
		this.instructingCourse = instructingCourse;
	}

	public FacultyDesignation getDesignation() {
		return designation;
	}

	public void setDesignation(FacultyDesignation designation) {
		this.designation = designation;
	}

	public List<CourseModel> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseModel> courses) {
		this.courses = courses;
	}

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

	@Override
	public String toString() {
		return "FacultyModel [empId=" + empId + ", instructingCourse=" + instructingCourse + ", designation="
				+ designation + ", courses=" + courses + ", joined_at=" + joined_at + ", exp=" + exp + "]";
	}

}
