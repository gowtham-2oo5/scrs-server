package com.scrs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_categories")
public class CourseCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true)
	private String title;

	@Column(name = "description")
	private String description;

	@Column(nullable = false)
	private Integer minSessionsPerWeek = 0;

	@Column(nullable = false)
	private Integer maxSessionsPerWeek = 0;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@JsonIgnore
	private List<CourseModel> courses = new ArrayList<>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMinSessionsPerWeek() {
		return minSessionsPerWeek;
	}

	public void setMinSessionsPerWeek(Integer minSessionsPerWeek) {
		this.minSessionsPerWeek = minSessionsPerWeek;
	}

	public Integer getMaxSessionsPerWeek() {
		return maxSessionsPerWeek;
	}

	public void setMaxSessionsPerWeek(Integer maxSessionsPerWeek) {
		this.maxSessionsPerWeek = maxSessionsPerWeek;
	}

	public List<CourseModel> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseModel> courses) {
		this.courses = courses;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CourseCategory that = (CourseCategory) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "CourseCategory [id=" + id + ", title=" + title + ", description=" + description
				+ ", minSessionsPerWeek=" + minSessionsPerWeek + ", maxSessionsPerWeek=" + maxSessionsPerWeek
				+ ", courses=" + courses + "]";
	}

	public void addCourse(CourseModel course) {
		if (course == null) {
			throw new IllegalArgumentException("Course cannot be null");
		}
		if (!this.courses.contains(course)) {
			this.courses.add(course);
			course.setCategory(this); // Maintain bidirectional relationship
		}
	}

	public void removeCourse(CourseModel course) {
		if (course == null) {
			return;
		}
		if (this.courses.remove(course)) {
			course.setCategory(null); // Break bidirectional relationship
		}
	}

}
