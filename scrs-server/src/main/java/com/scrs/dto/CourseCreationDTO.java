package com.scrs.dto;

import java.util.List;
import java.util.UUID;

public class CourseCreationDTO {

	private String courseCode;
	private String courseTitle;
	private String courseDesc;
	private String courseCategory; // title
	private String courseIncharge; // empId of faculty
	private String offeringDept; // SN of the offering dept
	private List<String> targetDepts; // List of SNs of target Deptrs
	private List<String> targetSpecs;
	private Boolean isOpenForAll;
	private String forStudentsOfYear;
	private Double L, T, P, S;
	private String preReqCourse;

	public String getPreReqCourse() {
		return preReqCourse;
	}

	public void setPreReqCourse(String preReqCourse) {
		this.preReqCourse = preReqCourse;
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

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

	public String getCourseIncharge() {
		return courseIncharge;
	}

	public void setCourseIncharge(String courseIncharge) {
		this.courseIncharge = courseIncharge;
	}

	public String getOfferingDept() {
		return offeringDept;
	}

	public void setOfferingDept(String offeringDept) {
		this.offeringDept = offeringDept;
	}

	public List<String> getTargetDepts() {
		return targetDepts;
	}

	public void setTargetDepts(List<String> targetDepts) {
		this.targetDepts = targetDepts;
	}

	public List<String> getTargetSpec() {
		return targetSpecs;
	}

	public void setTargetSpec(List<String> targetSpecs) {
		this.targetSpecs = targetSpecs;
	}

	public Boolean getIsOpenForAll() {
		return isOpenForAll;
	}

	public void setIsOpenForAll(Boolean isOpenForAll) {
		this.isOpenForAll = isOpenForAll;
	}

	public String getForStudentsOfYear() {
		return forStudentsOfYear;
	}

	public void setForStudentsOfYear(String forStudentsOfYear) {
		this.forStudentsOfYear = forStudentsOfYear;
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

}
