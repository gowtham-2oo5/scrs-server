package com.scrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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

    @Column(name = "dept_name", nullable = false) // Specify column name explicitly
    private String deptName;

    @Column(name = "sn", unique = true, nullable = false)
    private String sn;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetch type for performance
    @JoinColumn(name = "hod_id", referencedColumnName = "id", nullable = true) // Ensure nullable aligns with DB schema
    @JsonIgnore
    private FacultyModel hod;

    @OneToMany(mappedBy = "offeringDept", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CourseModel> courses;

    @OneToMany(mappedBy = "dept", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SpecializationModel> specializations;

    @OneToMany(mappedBy = "dept", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
