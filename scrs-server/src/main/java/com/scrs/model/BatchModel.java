package com.scrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "batches")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BatchModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private YearEnum currentYear;
    private SemesterEnum currentSem;
    private boolean eligibleForNextRegs;

    @OneToMany(mappedBy = "batch")
    @JsonIgnore
    private List<StudentModel> students;

    @OneToMany(mappedBy = "for_batch")
    private List<ClusterModel> clusters;

    @OneToMany(mappedBy = "template_batch")
    private List<ScheduleTemplate> templates;

    private Long studentCount;

}
