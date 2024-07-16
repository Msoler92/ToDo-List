package com.example.todolist.service;

import com.example.todolist.domain.Address;
import com.example.todolist.domain.Todo;
import com.example.todolist.domain.User;
import com.example.todolist.dto.TodoDataDto;
import com.example.todolist.dto.TodoFormDto;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.implementation.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoServiceImpl(todoRepository);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Todo todo = new Todo();
        todo.setUser(User.builder().username("testUser").address(Address.builder().country("testCountry").build()).build());

        Page<Todo> todoPage = new PageImpl<>(Collections.singletonList(todo));
        when(todoRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(todoPage);

        Page<TodoDataDto> result = todoService.findAll(Optional.empty(), Optional.empty(), pageable);

        assertEquals(1, result.getTotalElements());
        TodoDataDto dto = result.getContent().getFirst();
        assertEquals("testUser", dto.getUsername());
        assertEquals("testCountry", dto.getCountry());
        verify(todoRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testCreateTodo() {
        TodoFormDto todoFormDto = new TodoFormDto();
        todoFormDto.setUserId(1L);
        Todo todo = new Todo();
        todo.setUser(new User());
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoDataDto result = todoService.createTodo(todoFormDto);

        assertNotNull(result);
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void testFindById() {
        long id = 1L;
        Todo todo = new Todo();
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        TodoFormDto result = todoService.findById(id);

        assertNotNull(result);
        verify(todoRepository).findById(id);
    }

    @Test
    void testDelete() {
        long id = 1L;
        Todo todo = new Todo();
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        TodoDataDto result = todoService.delete(id);

        assertNotNull(result);
        verify(todoRepository).findById(id);
        verify(todoRepository).deleteById(id);
    }
}
