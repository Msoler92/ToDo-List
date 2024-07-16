package com.example.todolist.controller;

import com.example.todolist.dto.TodoFormDto;
import com.example.todolist.dto.TodoDataDto;
import com.example.todolist.service.TodoService;
import com.example.todolist.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    private final UserService userService;

    private static final String HOME_PAGE = "home";
    private static final String TODO_FORM_SUBMISSION_PAGE = "todo-submit";

    @GetMapping("/todos")
    public String getTodoList(
            Model model,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String titleFilter,
            @RequestParam(required = false) String usernameFilter
    ) {
        Pageable pageRequest = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<TodoDataDto> pageResult;
        if (titleFilter != null && titleFilter.trim().isEmpty()) {
            titleFilter = null;
        }
        if (usernameFilter != null && usernameFilter.trim().isEmpty()) {
            usernameFilter = null;
        }
        //TODO Allow asc and desc sorting
        pageResult = todoService.findAll(Optional.ofNullable(titleFilter), Optional.ofNullable(usernameFilter), pageRequest);
        List<Integer> pageIndex = IntStream.rangeClosed(1, pageResult.getTotalPages()).boxed().toList();
        model.addAttribute("todoPage", pageResult);
        model.addAttribute("pageNumbers", pageIndex);
        model.addAttribute("currentSort", sort);
        model.addAttribute("titleFilter", titleFilter);
        model.addAttribute("usernameFilter", usernameFilter);
        return HOME_PAGE;
    }

    @GetMapping("/todo")
    public String getCreationForm(Model model) {
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("todoFormDto", new TodoFormDto());
        return TODO_FORM_SUBMISSION_PAGE;
    }

    @PostMapping("/todo")
    public String postTodo(@Valid @ModelAttribute TodoFormDto todoFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userList", userService.findAll());
            model.addAttribute("todoFormDto", todoFormDto);
            model.addAttribute("errorMessage", "Invalid data");
            return TODO_FORM_SUBMISSION_PAGE;
        }
        todoService.createTodo(todoFormDto);
        return todoFormDto.getId() == 0 ? "redirect:/todo/todo" : "redirect:/todo/todos";
    }

    @GetMapping("/todo/{id}")
    public String getModificationForm(Model model, @PathVariable long id) {
        TodoFormDto todoFormDto = todoService.findById(id);
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("todoFormDto", todoFormDto   );
        return TODO_FORM_SUBMISSION_PAGE;
    }

    @PostMapping("/todo/{id}/delete")
    public String deleteTodo(Model model, @PathVariable long id) {
        todoService.delete(id);
        return "redirect:/todo/todos";
    }


}
