package com.example.todolist.service.implementation;

import com.example.todolist.dto.UserDto;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper converter;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.converter = new ModelMapper();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(user -> converter.map(user, UserDto.class)).toList();
    }

}
