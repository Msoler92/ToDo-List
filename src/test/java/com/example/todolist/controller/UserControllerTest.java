package com.example.todolist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.todolist.dto.UserFormDto;
import com.example.todolist.security.SecurityConfig;
import com.example.todolist.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.Collections;
@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testSignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("userFormDto"));
    }

    @Test
    void testSignup_Valid() throws Exception {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setName("user");
        userFormDto.setUsername("testuser");
        userFormDto.setPassword("password");
        userFormDto.setStreet("street");
        userFormDto.setCity("city");
        userFormDto.setZipcode("zipcode");
        userFormDto.setCountry("country");

        when(userService.existsByUsername(userFormDto.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(userFormDto.getPassword())).thenReturn("encodedPassword");

        mockMvc.perform(post("/signup")
                        .flashAttr("userFormDto", userFormDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("signup-success"));

        verify(userService).existsByUsername(userFormDto.getUsername());
        verify(passwordEncoder).encode("password");
        verify(userService).save(any(UserFormDto.class));
    }

    @Test
    void testSignup_UsernameExists() throws Exception {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setName("existinguser");
        userFormDto.setUsername("testuser");
        userFormDto.setPassword("password");
        userFormDto.setStreet("street");
        userFormDto.setCity("city");
        userFormDto.setZipcode("zipcode");
        userFormDto.setCountry("country");

        when(userService.existsByUsername(userFormDto.getUsername())).thenReturn(true);
        when(userService.findAll()).thenReturn(Collections.emptyList());


        mockMvc.perform(post("/signup")
                        .flashAttr("userFormDto", userFormDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("userList"))
                .andExpect(model().attributeExists("userFormDto"))
                .andExpect(model().attributeExists("errorMessage"));

        verify(userService).existsByUsername(userFormDto.getUsername());
        verify(userService, never()).save(any(UserFormDto.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void testSignup_InvalidData() throws Exception {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("testuser");
        userFormDto.setPassword("password");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        when(userService.existsByUsername(userFormDto.getUsername())).thenReturn(false);
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/signup")
                        .flashAttr("userFormDto", userFormDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("userList"))
                .andExpect(model().attributeExists("userFormDto"))
                .andExpect(model().attributeExists("errorMessage"));

        verify(userService).existsByUsername(userFormDto.getUsername());
        verify(userService, never()).save(any(UserFormDto.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
}

