package com.roasteg.todo.dto;

import lombok.Data;

@Data
public class AuthDto {
    private String email;
    private String name;
    private String password;
}
