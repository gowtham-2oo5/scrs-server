package com.scrs.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculty")
@DiscriminatorValue("fac")
public class FacultyModel extends UserModel {

	private String empId;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private DepartmentModel department;

	private Date joinedAt;

	public List<CourseModel> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseModel> courses) {
		this.courses = courses;
	}

	@Column(name = "instructing_course")
	private String instructingCourse;

	@Enumerated(EnumType.STRING)
	private FacultyDesignation designation;

	@OneToMany(mappedBy = "cc")
	private List<CourseModel> courses;

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

	public Date getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
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
