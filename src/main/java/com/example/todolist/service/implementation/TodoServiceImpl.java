package com.example.todolist.service.implementation;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.User;
import com.example.todolist.dto.TodoFormDto;
import com.example.todolist.dto.TodoDataDto;
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
        TypeMap<Todo, TodoDataDto> propertyMapper = converter.createTypeMap(Todo.class, TodoDataDto.class);
        propertyMapper.addMappings(mapper -> mapper.map(todo -> todo.getUser().getUsername(), TodoDataDto::setUsername));
        propertyMapper.addMappings(mapper -> mapper.map(todo -> todo.getUser().getAddress().getCountry(), TodoDataDto::setCountry));

        TypeMap<Todo, TodoFormDto> propertyMapper2 = converter.createTypeMap(Todo.class, TodoFormDto.class);
        propertyMapper2.addMappings(mapper -> mapper.map(todo -> todo.getUser().getId(), TodoFormDto::setUserId));
    }

    @Override
    public Page<TodoDataDto> findAll(Optional<String> title, Optional<String> username, Pageable pageable) {
        return todoRepository.findAll(TodoSpecifications.queryWithFilters(title, username), pageable).map(todo -> converter.map(todo, TodoDataDto.class));
    }

    @Override
    public TodoDataDto createTodo(TodoFormDto todoDto) {
        User user = User.builder().id(todoDto.getUserId()).build();
        return converter.map(todoRepository.save(Todo.builder()
                .id(todoDto.getId())
                .title(todoDto.getTitle())
                .completed(todoDto.isCompleted())
                .user(user)
                .build()), TodoDataDto.class);
    }

    @Override
    public TodoFormDto findById(long id) {
        return converter.map(todoRepository.findById(id).orElse(new Todo()), TodoFormDto.class);
    }

    @Override
    public TodoDataDto delete(long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()) {
            todoRepository.deleteById(id);
        }
        return converter.map(todo.orElse(new Todo()), TodoDataDto.class);
    }

}
