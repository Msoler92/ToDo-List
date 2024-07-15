package com.example.todolist.service.implementation;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.User;
import com.example.todolist.dto.NewTodoDto;
import com.example.todolist.dto.TodoDto;
import com.example.todolist.helper.TodoSpecifications;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<TodoDto> findAll(Optional<String> title, Optional<String> username, Pageable pageable) {
        return todoRepository.findAll(TodoSpecifications.queryWithFilters(title, username), pageable).map(todo -> converter.map(todo, TodoDto.class));
    }

    @Override
    public TodoDto createTodo(NewTodoDto todoDto) {
        User user = User.builder().id(todoDto.getUserId()).build();
        return converter.map(todoRepository.save(Todo.builder()
                .title(todoDto.getTitle())
                .completed(todoDto.isCompleted())
                .user(user)
                .build()), TodoDto.class);
    }

}
