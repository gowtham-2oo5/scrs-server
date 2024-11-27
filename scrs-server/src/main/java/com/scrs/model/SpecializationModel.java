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
@Table(name = "specs")
public class SpecializationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String specName;

	@Column(unique = true)
	private String sn;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private DepartmentModel dept;

	@OneToMany(mappedBy = "specialization")
	@JsonIgnore
	private List<StudentModel> students;

	@OneToMany(mappedBy = "spec")
	private List<CourseModel> courses;

	public DepartmentModel getDept() {
		return dept;
	}

	public void setDept(DepartmentModel dept) {
		this.dept = dept;
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

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Override
	public String toString() {
		return "SpecializationModel [id=" + id + ", specName=" + specName + ", sn=" + sn + ", dept=" + dept
				+ ", students=" + students + ", courses=" + courses + "]";
	}

}
