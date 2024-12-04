package com.scrs.model;

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

    @OneToMany(mappedBy = "cluster")
    private List<Section> sections;

    @OneToMany(mappedBy = "cluster")
    private List<CourseModel> courses;

}
