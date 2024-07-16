package com.example.todolist.service;

import com.example.todolist.dto.UserFormDto;
import com.example.todolist.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<UserDto> findAll();
    UserDto save(UserFormDto userDto);
    boolean existsByUsername(String username);
}
