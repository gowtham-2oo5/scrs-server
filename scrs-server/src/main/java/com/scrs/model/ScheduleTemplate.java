package com.scrs.model;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "scheduleTemplate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleSlot> schedules;

}
