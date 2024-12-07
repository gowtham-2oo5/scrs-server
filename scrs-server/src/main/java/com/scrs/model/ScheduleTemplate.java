package com.scrs.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedule_templates")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @OneToOne
    @JoinColumn(name = "cluster_id")
    private ClusterModel cluster;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleSlot> slots;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at", insertable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

}
