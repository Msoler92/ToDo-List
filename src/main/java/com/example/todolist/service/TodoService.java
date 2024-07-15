package com.example.todolist.service;

import com.example.todolist.dto.TodoFormDto;
import com.example.todolist.dto.TodoDataDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface TodoService {
    Page<TodoDataDto> findAll(Optional<String> title, Optional<String> username, Pageable pageable);
    TodoDataDto createTodo(TodoFormDto todoDto);

    TodoFormDto findById(long id);

}
