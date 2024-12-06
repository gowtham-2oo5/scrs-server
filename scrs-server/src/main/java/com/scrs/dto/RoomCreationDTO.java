package com.scrs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoomCreationDTO {

    private Integer floor;
    private String block;
    private Integer number;
    private Integer capacity;
    private String roomType;


}
