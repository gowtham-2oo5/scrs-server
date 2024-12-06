package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CourseDTO {

    private UUID id;
    private String courseCode;
    private String courseTitle;
    private String courseDesc;
    private String courseIncharge;
    private String courseInchargeId;

    private String offeringDept;
    private List<String> targetDepartments;
    private List<String> targetSpecializations;
    private String whoCanRegister;
    private Double credits;
    private Double L, T, P, S;


}
