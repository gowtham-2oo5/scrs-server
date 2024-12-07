package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ClusterDTO {

    private UUID id;
    private UUID templateId;
    private String name;
    private String batch;
    private Long studentCount;
    private Long sectionsCount;

}
