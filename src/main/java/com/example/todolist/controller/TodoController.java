package com.example.todolist.controller;

import com.example.todolist.dto.TodoDto;
import com.example.todolist.service.TodoService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/getAll")
    public String getTodoList(
            Model model,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) Optional<String> title,
            @RequestParam(required = false) Optional<String> username) {
        //TODO map sort based on DTO properties instead of Entity properties directly?

        Pageable pageRequest = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<TodoDto> pageResult = todoService.findAll(title, username, pageRequest);
        List<Integer> pageIndex = IntStream.rangeClosed(1, pageResult.getTotalPages()).boxed().toList();
        model.addAttribute("todoPage", pageResult);
        model.addAttribute("pageNumbers", pageIndex);
        model.addAttribute("currentSort", sort);
        return "home";
    }

}
