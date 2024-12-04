package com.scrs.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "batches")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BatchModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true, nullable = false)
	private String name;

	private YearEnum currentYear;
	private SemesterEnum currentSem;
	private boolean eligibleForNextRegs;

	@OneToMany(mappedBy = "batch")
	@JsonIgnore
	private List<StudentModel> students;

	private Long studentCount;

}
