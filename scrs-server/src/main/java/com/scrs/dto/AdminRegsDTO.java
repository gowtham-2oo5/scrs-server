package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AdminRegsDTO {

    private String name;
    private String username;
    private String email;
    private String password;
    private String contact;
    private Date dob;

    public AdminRegsDTO() {
        super();
    }

    public AdminRegsDTO(String name, String username, String email, String password, String contact,
                        boolean isSuperAdmin, Date dob) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.dob = dob;
    }

}
