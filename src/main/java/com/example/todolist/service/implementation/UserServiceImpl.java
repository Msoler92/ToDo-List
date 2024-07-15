package com.example.todolist.service.implementation;

import com.example.todolist.domain.Address;
import com.example.todolist.domain.User;
import com.example.todolist.dto.UserFormDto;
import com.example.todolist.dto.UserDto;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public UserDto save(UserFormDto userDto) {
        Address address = Address.builder()
                .city(userDto.getCity())
                .street(userDto.getStreet())
                .country(userDto.getCountry())
                .zipcode(userDto.getZipcode()).build();
        User user = User.builder()
                .name(userDto.getName())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .address(address).build();

        return converter.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }
}
