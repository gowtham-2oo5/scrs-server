package com.scrs.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountConfirmationMailDTO {

    private String name;
    private String email;
    private String password;
    private String username;

}
