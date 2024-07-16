package com.example.todolist.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.todolist.domain.User;
import com.example.todolist.dto.UserDto;
import com.example.todolist.dto.UserFormDto;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testFindAll() {
        User user = new User();
        user.setUsername("johndoe");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserDto> result = userService.findAll();

        assertEquals(1, result.size());
        UserDto dto = result.get(0);
        assertEquals("johndoe", dto.getUsername());
        verify(userRepository).findAll();
    }

    @Test
    void testSave() {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setName("John Doe");
        userFormDto.setUsername("johndoe");
        userFormDto.setPassword("password");
        userFormDto.setCity("City");
        userFormDto.setStreet("Street");
        userFormDto.setCountry("Country");
        userFormDto.setZipcode("12345");

        User user = new User();
        user.setName("John Doe");
        user.setUsername("johndoe");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.save(userFormDto);

        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testExistsByUsername() {
        String username = "johndoe";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean result = userService.existsByUsername(username);

        assertTrue(result);
        verify(userRepository).existsByUsername(username);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "johndoe";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails result = userService.loadUserByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        String username = "johndoe";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
        verify(userRepository).findByUsername(username);
    }
}

