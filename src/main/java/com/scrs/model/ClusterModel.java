package com.scrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clusters")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClusterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne
    private BatchModel for_batch;

    @OneToMany(mappedBy = "cluster")
    @JsonIgnore
    private List<StudentModel> students;

    private Long studentCount;
    private Long sectionsCount;

    @OneToOne(mappedBy = "cluster", cascade = CascadeType.ALL)
    private ScheduleTemplate template;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    private List<SectionModel> sections;
}
