package com.example.todolist.controller;

import com.example.todolist.dto.UserFormDto;
import com.example.todolist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute UserFormDto userFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userList", userService.findAll());
            model.addAttribute("userFormDto", userFormDto);
            model.addAttribute("errorMessage", "Invalid data");
            return "signup";
        }

        String encodedPassword = passwordEncoder.encode(userFormDto.getPassword());
        userFormDto.setPassword(encodedPassword);
        userService.save(userFormDto);
        return "redirect:/todo/todos";
    }
}
