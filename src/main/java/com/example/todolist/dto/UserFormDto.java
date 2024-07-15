package com.example.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFormDto {
    private String name;
    private String username;
    private String password;
    private String street;
    private String city;
    private String zipcode;
    private String country;
}
