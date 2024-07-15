package com.example.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewTodoDto {
    private long userId;
    private String title;
    private boolean completed;
}
