package com.scrs.dto;

import com.scrs.model.FacultyModel;
import com.scrs.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FacultyDTO {
    private String name;
    private String email;
    private String contact;
    private String profilePicture;
    private Date dob;
    private String empId;
    private String department;
    private String designation;
    private String exp;

    public FacultyDTO(UserModel user, FacultyModel facultyModel) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.contact = user.getContact();
        this.profilePicture = user.getProfilePicture();
        this.dob = user.getDob();
        this.empId = facultyModel.getEmpId();
        this.department = facultyModel.getDepartment().getDeptName();
        this.designation = facultyModel.getDesignation().name();
        this.exp = facultyModel.getExp();
    }

    public FacultyDTO() {
    }


}
