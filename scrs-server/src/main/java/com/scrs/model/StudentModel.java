package com.scrs.model;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "scrs_students")
@DiscriminatorValue("ROLE_STUDENT")
public class StudentModel extends UserModel {

	private String regNum;

	@ManyToOne
	@JoinColumn(name = "specialization_id", nullable = false)
	private SpecializationModel specialization;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private DepartmentModel dept;

	@ManyToOne
	@JoinColumn(name = "batch_id", nullable = false)
	private BatchModel batch;

	public StudentModel() {
		super();
	}

	public StudentModel(String name, String username, String email, String password, String contact, UserRole userRole,
			String profilePicture, String regNum, SpecializationModel specialization, DepartmentModel dept,
			BatchModel batch, Date joinedAt, Date dob) {
		super(name, username, email, password, contact, userRole, profilePicture, dob);
		this.regNum = regNum;
		this.specialization = specialization;
		this.dept = dept;
		this.batch = batch;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public SpecializationModel getSpecialization() {
		return specialization;
	}

	public void setSpecialization(SpecializationModel specialization) {
		this.specialization = specialization;
	}

	public DepartmentModel getDept() {
		return dept;
	}

	public void setDept(DepartmentModel dept) {
		this.dept = dept;
	}

	public BatchModel getBatch() {
		return batch;
	}

	public void setBatch(BatchModel batch) {
		this.batch = batch;
	}

}
