package com.scrs.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course_categories")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    private ClusterModel cluster; // The cluster this category belongs to


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CourseModel> courses = new ArrayList<>();

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
