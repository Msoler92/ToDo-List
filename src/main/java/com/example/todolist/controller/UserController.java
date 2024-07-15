package com.example.todolist.controller;

import com.example.todolist.dto.UserFormDto;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new UserFormDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Model model, UserFormDto dto) {
        String encodedPasword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPasword);
        userService.save(dto);
        return "redirect:/todo/todos";
    }

}
