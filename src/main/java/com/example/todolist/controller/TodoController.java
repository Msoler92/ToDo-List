package com.example.todolist.controller;

import com.example.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/getAllTodo")
    public String getTodoList(Model model) {
        model.addAttribute("todoList", todoService.findAll());
        return "show-todo-list";
    }

}
