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
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;
    private boolean completed;
}
