package com.scrs.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "scrs_courses")
public class CourseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String courseCode;

	@Column(nullable = false)
	private String courseTitle;

	@Column(length = 500)
	private String courseDesc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private CourseCategory category;

	@ManyToOne
	@JoinColumn(name = "cc_id", referencedColumnName = "id")
	private FacultyModel incharge;

	@ManyToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "id")
	private DepartmentModel offeringDept;

	@ManyToMany
	@JoinTable(name = "course_target_depts", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "dept_id"))
	private List<DepartmentModel> targetDepts;

	@ManyToMany
	@JoinTable(name = "course_target_specs", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "spec_id"))
	private List<SpecializationModel> targetSpecializations;

	@Column(name = "open_for_all", nullable = false)
	private boolean openForAll;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private YearEnum year;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prereq_course_id")
	private CourseModel preReqCourse;

	private Double L, T, P, S;

	public Double getCredits() {
		return (L != null ? L : 0) + (T != null ? T / 2 : 0) + (P != null ? P / 2 : 0) + (S != null ? S / 4 : 0);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public CourseCategory getCategory() {
		return category;
	}

	public void setCategory(CourseCategory category) {
		this.category = category;
	}

	public FacultyModel getIncharge() {
		return incharge;
	}

	public void setIncharge(FacultyModel incharge) {
		this.incharge = incharge;
	}

	public DepartmentModel getOfferingDept() {
		return offeringDept;
	}

	public void setOfferingDept(DepartmentModel offeringDept) {
		this.offeringDept = offeringDept;
	}

	public List<DepartmentModel> getTargetDepts() {
		return targetDepts;
	}

	public void setTargetDepts(List<DepartmentModel> targetDepts) {
		this.targetDepts = targetDepts;
	}

	public List<SpecializationModel> getTargetSpecializations() {
		return targetSpecializations;
	}

	public void setTargetSpecializations(List<SpecializationModel> targetSpecializations) {
		this.targetSpecializations = targetSpecializations;
	}

	public boolean isOpenForAll() {
		return openForAll;
	}

	public void setOpenForAll(boolean openForAll) {
		this.openForAll = openForAll;
	}

	public YearEnum getYear() {
		return year;
	}

	public void setYear(YearEnum year) {
		this.year = year;
	}

	public CourseModel getPreReqCourse() {
		return preReqCourse;
	}

	public void setPreReqCourse(CourseModel preReqCourse) {
		this.preReqCourse = preReqCourse;
	}

	public Double getL() {
		return L;
	}

	public void setL(Double l) {
		L = l;
	}

	public Double getT() {
		return T;
	}

	public void setT(Double t) {
		T = t;
	}

	public Double getP() {
		return P;
	}

	public void setP(Double p) {
		P = p;
	}

	public Double getS() {
		return S;
	}

	public void setS(Double s) {
		S = s;
	}

	@Override
	public String toString() {
		return "SAVED WITH ID: " + id+" TITLE: "+ courseTitle+" CREDITS: "+ getCredits();
	}

}
