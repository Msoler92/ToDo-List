package com.example.todolist.dto;

import com.example.todolist.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewTodoDto {
    private User user;
    private String title;
    private boolean completed;
}
