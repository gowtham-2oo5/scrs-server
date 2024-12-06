package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class StudentRegsDTO {

    private String name;
    private String contact;
    private String email;
    private String regNum;
    private String dept;
    private String spec;
    private String batch;
    private Date dob;


}
