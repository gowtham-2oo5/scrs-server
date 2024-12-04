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
import lombok.*;

@Entity
@Table(name = "scrs_faculty")
@DiscriminatorValue("ROLE_FACULTY")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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

}
