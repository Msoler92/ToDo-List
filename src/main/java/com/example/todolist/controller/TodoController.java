package com.example.todolist.controller;

import com.example.todolist.dto.NewTodoDto;
import com.example.todolist.dto.TodoDto;
import com.example.todolist.service.TodoService;
import com.example.todolist.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    private final UserService userService;

    @GetMapping("/getAll")
    public String getTodoList(
            Model model,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String titleFilter,
            @RequestParam(required = false) String usernameFilter
    ) {
        Pageable pageRequest = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<TodoDto> pageResult;
        if (titleFilter != null && titleFilter.trim().isEmpty()) {
            titleFilter = null;
        }
        if (usernameFilter != null && usernameFilter.trim().isEmpty()) {
            usernameFilter = null;
        }
        //TODO Verify case pageresult = null for proper Thymeleaf switch display
        pageResult = todoService.findAll(Optional.ofNullable(titleFilter), Optional.ofNullable(usernameFilter), pageRequest);
        List<Integer> pageIndex = IntStream.rangeClosed(1, pageResult.getTotalPages()).boxed().toList();
        model.addAttribute("todoPage", pageResult);
        model.addAttribute("pageNumbers", pageIndex);
        model.addAttribute("currentSort", sort);
        model.addAttribute("titleFilter", titleFilter);
        model.addAttribute("usernameFilter", usernameFilter);
        return "home";
    }

    @GetMapping("/new")
    public String getCreationForm(Model model) {
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("newTodo", new NewTodoDto());
        return "todo-submit";
    }

    @PostMapping("/new")
    public String postCreationForm(NewTodoDto todoDto, Model model, @RequestParam(required = false) boolean submitAnother) {
        todoService.createTodo(todoDto);
        if(submitAnother) {
            return "redirect:/todo/new";
        }
        return "redirect:/todo/getAll";
    }

}
