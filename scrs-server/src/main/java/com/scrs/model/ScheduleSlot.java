package com.scrs.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "schedule_slots")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String day;  // e.g., "Monday"
	private int slot;    // Slot number (1-9, for example)

	@ManyToOne
	@JoinColumn(name = "schedule_template_id")
	private ScheduleTemplate scheduleTemplate;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;  // Room associated with the slot

}
