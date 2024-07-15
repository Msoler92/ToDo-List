package com.example.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDataDto {
    private long id;
    private String title;
    private String username;
    private String country;
    private boolean completed;
}
