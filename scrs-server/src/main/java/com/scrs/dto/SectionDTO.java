package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class SectionDTO {

    private UUID sectionId;
    private UUID clusterId;
    private UUID courseId;
    private UUID courseCategoryId;
    private UUID roomId;
    private String facultyEmpId;
    private String secName;
    private String clusterName;
    private String courseName;
    private String courseCategoryName;
    private String facultyName;
    private String roomName;

}
