package com.example.todolist.service.implementation;

import com.example.todolist.domain.Address;
import com.example.todolist.domain.Todo;
import com.example.todolist.domain.User;
import com.example.todolist.dto.TodoDto;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper converter;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        this.converter = new ModelMapper();
        TypeMap<Todo, TodoDto> propertyMapper = converter.createTypeMap(Todo.class, TodoDto.class);
        propertyMapper.addMappings(mapper -> mapper.map(todo -> todo.getUser().getUsername(), TodoDto::setUsername));
        propertyMapper.addMappings(mapper -> mapper.map(todo -> todo.getUser().getAddress().getCountry(), TodoDto::setCountry));

    }

    @Override
    public List<TodoDto> findAll() {
        Address address = new Address(1, "Street", "City", "08032", "Country");
        User user1 = new User(1, "name1", "username1", "password1", address);
        User user2 = new User(2, "name2", "username2", "password2", address);

        return Stream.of(
                new Todo(1, "title1", true, user1),
                new Todo(2, "title2", false, user2)
        ).map(todo -> converter.map(todo, TodoDto.class)).collect(Collectors.toList());
    }
}
