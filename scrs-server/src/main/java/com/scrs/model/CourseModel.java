package com.scrs.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "scrs_courses")
public class CourseModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String courseCode;
	private String title;
	@Column(name = "description")
	private String desc;

	@Enumerated(EnumType.STRING)
	private CourseCategory category;

	@ManyToOne
	@JoinColumn(name = "cc_id", referencedColumnName = "id")
	private FacultyModel cc;

	@ManyToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "id")
	private DepartmentModel dept; // Parent department

	@ManyToMany
	@JoinTable(name = "course_target_depts", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "dept_id"))
	private List<DepartmentModel> targetDepts; // Open for students of these departments

	@ManyToOne
	@JoinColumn(name = "spec_id", referencedColumnName = "id")
	private SpecializationModel spec;

	private boolean openForAll; // For common courses

	@Enumerated(EnumType.STRING)
	private YearEnum year;

	public YearEnum getYear() {
		return year;
	}

	public void setYear(YearEnum year) {
		this.year = year;
	}

	public List<DepartmentModel> getTargetDepts() {
		return targetDepts;
	}

	public void setTargetDepts(List<DepartmentModel> targetDepts) {
		this.targetDepts = targetDepts;
	}

	public SpecializationModel getSpec() {
		return spec;
	}

	public void setSpec(SpecializationModel spec) {
		this.spec = spec;
	}

	public boolean isOpenForAll() {
		return openForAll;
	}

	public void setOpenForAll(boolean openForAll) {
		this.openForAll = openForAll;
	}

	private Double L, T, P, S;

	public Double getCredits() {
		return (L != null ? L : 0) + (T != null ? T / 2 : 0) + (P != null ? P / 2 : 0) + (S != null ? S / 4 : 0);
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public CourseCategory getCategory() {
		return category;
	}

	public void setCategory(CourseCategory category) {
		this.category = category;
	}

	public FacultyModel getCc() {
		return cc;
	}

	public void setCc(FacultyModel cc) {
		this.cc = cc;
	}

	public DepartmentModel getDept() {
		return dept;
	}

	public void setDept(DepartmentModel dept) {
		this.dept = dept;
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

	@Override
	public String toString() {
		return "CourseModel [id=" + id + ", courseCode=" + courseCode + ", title=" + title + ", desc=" + desc
				+ ", category=" + category + ", cc=" + cc + ", dept=" + dept + ", L=" + L + ", T=" + T + ", P=" + P
				+ ", S=" + S + "]";
	}

}
