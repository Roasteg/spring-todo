package com.roasteg.todo.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String token;
}
