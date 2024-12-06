package com.scrs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "scrs_students")
@DiscriminatorValue("ROLE_STUDENT")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("all")
public class StudentModel extends UserModel {

    private String regNum;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = true)
    private SpecializationModel specialization;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentModel dept;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private BatchModel batch;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private ClusterModel cluster;

    @ManyToMany
    private List<SectionModel> registeredSections;

    public StudentModel(String name, String username, String email, String password, String contact, UserRole userRole,
                        String profilePicture, String regNum, SpecializationModel specialization, DepartmentModel dept,
                        BatchModel batch, Date joinedAt, Date dob) {
        super(name, username, email, password, contact, userRole, profilePicture, dob);
        this.regNum = regNum;
        this.specialization = specialization;
        this.dept = dept;
        this.batch = batch;
    }
}
