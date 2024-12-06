package com.scrs.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ScheduleTemplateDTO {

    List<ScheduleSlotDTO> slots;
    private UUID id;
    private String title;
    private String cluster;

}
