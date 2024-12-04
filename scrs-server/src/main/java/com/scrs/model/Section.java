package com.scrs.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "sections")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sectionName; // e.g., "Section A"

    @ManyToOne
    private ClusterModel cluster; // A section belongs to a cluster

    @ManyToOne
    private CourseModel course; // A section is for a specific course

    @ManyToOne
    private Room room; // A section is allocated a room

    @ManyToOne
    private ScheduleTemplate scheduleTemplate; // A section uses a schedule template

    //@OneToMany(mappedBy = "section")
    //private List<StudentRegistration> studentRegistrations;

}
