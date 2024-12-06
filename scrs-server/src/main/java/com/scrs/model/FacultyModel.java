package com.scrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<SectionModel> sections;

    private Date joined_at;
    private String exp;

}
