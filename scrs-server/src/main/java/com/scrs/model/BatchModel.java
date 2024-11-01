package com.scrs.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "batches")
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
	private List<StudentModel> students;

	public SemesterEnum getCurrentSem() {
		return currentSem;
	}

	public void setCurrentSem(SemesterEnum currentSem) {
		this.currentSem = currentSem;
	}

	public List<StudentModel> getStudents() {
		return students;
	}

	public void setStudents(List<StudentModel> students) {
		this.students = students;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public YearEnum getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(YearEnum currentYear) {
		this.currentYear = currentYear;
	}

	public boolean isEligibleForNextRegs() {
		return eligibleForNextRegs;
	}

	public void setEligibleForNextRegs(boolean eligibleForNextRegs) {
		this.eligibleForNextRegs = eligibleForNextRegs;
	}

	@Override
	public String toString() {
		return "BatchModel [id=" + id + ", name=" + name + ", currentYear=" + currentYear + ", currentSem=" + currentSem
				+ ", eligibleForNextRegs=" + eligibleForNextRegs + ", students=" + students + "]";
	}

}
