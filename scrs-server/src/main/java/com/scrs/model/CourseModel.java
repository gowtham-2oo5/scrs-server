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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "scrs_courses")
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("all")
public class CourseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	private ClusterModel cluster;

	@Column(nullable = false, unique = true)
	private String courseCode;

	@Column(nullable = false)
	private String courseTitle;

	@Column(length = 500)
	private String courseDesc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnore
	private CourseCategory category;

	@ManyToOne
	@JoinColumn(name = "cc_id", referencedColumnName = "id")
	@JsonIgnore
	private FacultyModel incharge;

	@ManyToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "id")
	@JsonIgnore
	private DepartmentModel offeringDept;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "course_target_depts", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "dept_id") )
	@JsonIgnore
	private List<DepartmentModel> targetDepts;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "course_target_specs", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "spec_id"))
	@JsonIgnore
	private List<SpecializationModel> targetSpecializations;

	@Column(name = "open_for_all", nullable = false)
	private boolean openForAll;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private YearEnum year;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prereq_course_id")
	@JsonIgnore
	private CourseModel preReqCourse;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "students_registered_in_course", joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id"))
	@JsonIgnore
	private List<StudentModel> registeredStudents;

	private Double L, T, P, S;

	public Double getCredits() {
		return (L != null ? L : 0) + (T != null ? T / 2 : 0) + (P != null ? P / 2 : 0) + (S != null ? S / 4 : 0);
	}
}
