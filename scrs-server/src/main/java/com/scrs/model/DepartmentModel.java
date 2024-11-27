package com.scrs.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "depts")
public class DepartmentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String deptName;

	@Column(unique = true)
	private String sn;

	@ManyToOne
	@JoinColumn(name = "hod_id", referencedColumnName = "id")
	//@JsonIgnore
	private FacultyModel hod;

	@OneToMany(mappedBy = "dept")
	@JsonIgnore
	private List<CourseModel> courses;

	@OneToMany(mappedBy = "dept")
	@JsonIgnore
	private List<SpecializationModel> specializations;

	@OneToMany(mappedBy = "dept")
	@JsonIgnore
	private List<StudentModel> students;

	public List<SpecializationModel> getSpecializations() {
		return specializations;
	}

	public void setSpecializations(List<SpecializationModel> specializations) {
		this.specializations = specializations;
	}

	public List<StudentModel> getStudents() {
		return students;
	}

	public void setStudents(List<StudentModel> students) {
		this.students = students;
	}

	public List<CourseModel> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseModel> courses) {
		this.courses = courses;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public FacultyModel getHod() {
		return hod;
	}

	public void setHod(FacultyModel hod) {
		this.hod = hod;
	}

	@Override
	public String toString() {
		return "DepartmentModel [id=" + id + ", deptName=" + deptName + ", sn=" + sn + ", hod=" + hod + "]";
	}

	public boolean isEmpty() {
		return this == null;
	}

}
