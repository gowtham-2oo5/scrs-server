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
    private String roomNum;
    private Integer capacity;

    @OneToMany(mappedBy = "room")
    private List<Section> sections;
}
