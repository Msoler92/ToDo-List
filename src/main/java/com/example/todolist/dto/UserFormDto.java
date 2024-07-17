package com.example.todolist.dto;

import com.example.todolist.validation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFormDto {
    private static final String NOT_BLANK_ERROR = "Field must not be blank";

    @NotBlank (message = NOT_BLANK_ERROR)
    private String name;
    @NotBlank (message = NOT_BLANK_ERROR)
    @UniqueUsername
    private String username;
    @NotBlank (message = NOT_BLANK_ERROR)
    private String password;
    @NotBlank (message = NOT_BLANK_ERROR)
    private String street;
    @NotBlank (message = NOT_BLANK_ERROR)
    private String city;
    @NotBlank (message = NOT_BLANK_ERROR)
    private String zipcode;
    @NotBlank (message = NOT_BLANK_ERROR)
    private String country;
}
