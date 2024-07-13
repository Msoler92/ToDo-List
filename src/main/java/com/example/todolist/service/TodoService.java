package com.example.todolist.service;

import com.example.todolist.dto.TodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TodoService {
    Page<TodoDto> findAll(Pageable pageable);
}
