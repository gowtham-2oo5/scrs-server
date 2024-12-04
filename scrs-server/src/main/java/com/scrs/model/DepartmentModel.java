package com.scrs.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "depts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String deptName;

	@Column(unique = true)
	private String sn;

	@ManyToOne
	@JoinColumn(name = "hod_id", referencedColumnName = "id")
	// @JsonIgnore
	private FacultyModel hod;
	
	@OneToMany(mappedBy = "offeringDept", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CourseModel> courses;

	@OneToMany(mappedBy = "dept")
	@JsonIgnore
	private List<SpecializationModel> specializations;

	@OneToMany(mappedBy = "dept")
	@JsonIgnore
	private List<StudentModel> students;

	@Column(name = "student_count", nullable = false)
	private Long studentCount;

	@PrePersist
	public void prePersist() {
		if (this.studentCount == null) {
			this.studentCount = 0L; // Set default value
		}
	}


}
