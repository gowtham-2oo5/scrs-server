package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CourseCreationDTO {

    private String courseCode;
    private String courseTitle;
    private String courseDesc;
    private String courseCategory; // title
    private String courseIncharge; // empId of faculty
    private String offeringDept; // SN of the offering dept
    private List<String> targetDepts; // List of SNs of target Deptrs
    private List<String> targetSpecs;
    private Boolean isOpenForAll;
    private String forStudentsOfYear;
    private Double L, T, P, S;
    private String preReqCourse;


}
