package com.example.todolist.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoFormDto {
    private long id;
    @Min(value = 1)
    private long userId;
    @NotBlank(message = "Title should not be blank")
    @Size(min = 1, max = 200, message="Title should be between 1 and 200 characters long")
    private String title;
    private boolean completed;
}
