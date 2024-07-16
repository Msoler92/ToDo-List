package com.example.todolist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.todolist.dto.TodoDataDto;
import com.example.todolist.dto.TodoFormDto;
import com.example.todolist.security.SecurityConfig;
import com.example.todolist.service.TodoService;
import com.example.todolist.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
@Import(SecurityConfig.class)
@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @MockBean
    private UserService userService;


    @Test
    void testGetTodoList() throws Exception {
        Page<TodoDataDto> todoPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        when(todoService.findAll(any(Optional.class), any(Optional.class), any(Pageable.class))).thenReturn(todoPage);

        mockMvc.perform(get("/todo/todos")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "id")
                        .param("titleFilter", "title")
                        .param("usernameFilter", "username"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("todoPage"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(model().attributeExists("currentSort"))
                .andExpect(model().attributeExists("titleFilter"))
                .andExpect(model().attributeExists("usernameFilter"));

        verify(todoService).findAll(any(Optional.class), any(Optional.class), any(Pageable.class));
    }

    @Test
    void testGetCreationForm() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/todo/todo"))
                .andExpect(status().isOk())
                .andExpect(view().name("todo-submit"))
                .andExpect(model().attributeExists("userList"))
                .andExpect(model().attributeExists("todoFormDto"));

        verify(userService).findAll();
    }

    @Test
    void testPostTodo_Valid() throws Exception {
        TodoFormDto todoFormDto = new TodoFormDto();
        todoFormDto.setId(0L);
        todoFormDto.setUserId(1L);
        todoFormDto.setTitle("title");
        when(todoService.createTodo(any(TodoFormDto.class))).thenReturn(new TodoDataDto());

        mockMvc.perform(post("/todo/todo")
                .flashAttr("todoFormDto", todoFormDto)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todo/todo"))
        ;

        verify(todoService).createTodo(any(TodoFormDto.class));
    }

    @Test
    void testPostTodo_Invalid() throws Exception {
        TodoFormDto todoFormDto = new TodoFormDto();
        todoFormDto.setUserId(0L);
        todoFormDto.setTitle("");
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/todo/todo")
                        .flashAttr("todoFormDto", todoFormDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("todo-submit"))
                .andExpect(model().attributeExists("userList"))
                .andExpect(model().attributeExists("todoFormDto"))
                .andExpect(model().attributeExists("errorMessage"));

        verify(userService).findAll();
        verify(todoService, never()).createTodo(any(TodoFormDto.class));
    }
    @WithMockUser
    @Test
    void testGetModificationForm() throws Exception {
        long id = 1L;
        TodoFormDto todoFormDto = new TodoFormDto();
        when(todoService.findById(id)).thenReturn(todoFormDto);
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/todo/todo/update")
                        .param("id", String.valueOf(id))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("todo-submit"))
                .andExpect(model().attributeExists("userList"))
                .andExpect(model().attributeExists("todoFormDto"));

        verify(todoService).findById(id);
        verify(userService).findAll();
    }
    @WithMockUser
    @Test
    void testDeleteTodo() throws Exception {
        long id = 1L;

        mockMvc.perform(post("/todo/todo/delete")
                        .param("id", String.valueOf(id))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todo/todos"));

        verify(todoService).delete(id);
    }
}
