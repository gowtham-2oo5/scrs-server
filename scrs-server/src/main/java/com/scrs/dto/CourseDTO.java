package com.scrs.dto;

import java.util.List;
import java.util.UUID;

public class CourseDTO {

	private UUID id;
	private String courseCode;
	private String courseTitle;
	private String courseDesc;
	private String courseIncharge;
	private String courseInchargeId;

	private String offeringDept;
	private List<String> targetDepartments;
	private List<String> targetSpecializations;
	private String whoCanRegister;
	private Double credits;
	private Double L, T, P, S;

	public String getCourseInchargeId() {
		return courseInchargeId;
	}

	public void setCourseInchargeId(String courseInchargeId) {
		this.courseInchargeId = courseInchargeId;
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

	public List<String> getTargetDepartments() {
		return targetDepartments;
	}

	public void setTargetDepartments(List<String> targetDepartments) {
		this.targetDepartments = targetDepartments;
	}

	public List<String> getTargetSpecializations() {
		return targetSpecializations;
	}

	public void setTargetSpecializations(List<String> targetSpecializations) {
		this.targetSpecializations = targetSpecializations;
	}

	public String getWhoCanRegister() {
		return whoCanRegister;
	}

	public void setWhoCanRegister(String whoCanRegister) {
		this.whoCanRegister = whoCanRegister;
	}

	public Double getCredits() {
		return credits;
	}

	public void setCredits(Double credits) {
		this.credits = credits;
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
