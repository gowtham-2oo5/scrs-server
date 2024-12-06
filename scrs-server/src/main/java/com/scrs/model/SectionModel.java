package com.scrs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "sections")
@Getter
@Setter
@ToString
public class SectionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private TimeSlotEnum timeSlot;

    @ManyToOne
    private ClusterModel cluster;

    @ManyToOne
    private CourseModel course;

    @ManyToOne
    private FacultyModel faculty;

    @ManyToOne
    private Room room;

}
