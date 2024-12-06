package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class FacultyRegsDTO {

    private String name;
    private String contact;
    private String mail;
    private String empId;
    private String dept;
    private Date dob;
    private String designation;
    private Date joined_at;
    private String exp;


}