package com.scrs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponse {

    String token;
    private Object data;
    private String role;
    private String username;

}
