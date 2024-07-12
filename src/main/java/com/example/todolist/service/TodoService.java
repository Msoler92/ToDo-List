package com.example.todolist.service;

import com.example.todolist.dto.TodoDto;
import java.util.List;


public interface TodoService {
    List<TodoDto> findAll();
}
