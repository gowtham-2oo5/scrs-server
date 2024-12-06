package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleSlotDTO {

    private String day;
    private String timeSlot;
    private String courseCategory;

}
