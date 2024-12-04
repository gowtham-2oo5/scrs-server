package com.scrs.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scrs_students")
@DiscriminatorValue("ROLE_STUDENT")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("all")
public class StudentModel extends UserModel {

	private String regNum;

	@ManyToOne
	@JoinColumn(name = "specialization_id", nullable = true)
	private SpecializationModel specialization;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private DepartmentModel dept;

	@ManyToOne
	@JoinColumn(name = "batch_id", nullable = false)
	private BatchModel batch;

	@OneToMany(mappedBy = "registeredStudents", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CourseModel> registeredCourses;

	public StudentModel(String name, String username, String email, String password, String contact, UserRole userRole,
						String profilePicture, String regNum, SpecializationModel specialization, DepartmentModel dept,
						BatchModel batch, Date joinedAt, Date dob) {
		super(name, username, email, password, contact, userRole, profilePicture, dob);
		this.regNum = regNum;
		this.specialization = specialization;
		this.dept = dept;
		this.batch = batch;
	}
}
