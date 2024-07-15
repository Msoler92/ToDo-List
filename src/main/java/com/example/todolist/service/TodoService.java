package com.example.todolist.service;

import com.example.todolist.dto.NewTodoDto;
import com.example.todolist.dto.TodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface TodoService {
    Page<TodoDto> findAll(Optional<String> title, Optional<String> username, Pageable pageable);
    TodoDto createTodo(NewTodoDto todoDto);

}
