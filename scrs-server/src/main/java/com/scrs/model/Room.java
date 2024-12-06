package com.scrs.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String block;
    private Integer floor;
    private Integer roomNum;
    private Integer capacity;
    private String roomType;

    @OneToMany(mappedBy = "room")
    private List<SectionModel> sections;

    @OneToMany(mappedBy = "room")
    private List<ScheduleSlot> bookedSlots;

    public String getRoomName() {
        return block + floor.toString() + getFormattedRoomNum();
    }

    private String getFormattedRoomNum() {
        return (roomNum > 10) ? "0" + roomNum.toString() : roomNum.toString();
    }
}
