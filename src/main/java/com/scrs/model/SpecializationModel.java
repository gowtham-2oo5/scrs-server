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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "specs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

	@ManyToMany(mappedBy = "targetSpecializations")
	private List<CourseModel> courses;

	
	private Long studentCount;

}
