package com.roasteg.todo.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roasteg.todo.dto.TodoDto;
import com.roasteg.todo.exception.TodoNotFoundException;
import org.springframework.stereotype.Service;

import com.roasteg.todo.model.Todo;
import com.roasteg.todo.repository.TodoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final ObjectMapper mapper;
    private final JwtService jwtService;

    public List<TodoDto> getAllTodos(String token) {
        return todoRepository
                .findByUser(jwtService.getUserFromToken(token))
                .stream()
                .map(todo -> mapper.convertValue(todo, TodoDto.class))
                .toList();
    }

    public TodoDto saveTodo(TodoDto todo, String token) {
        final Todo fromDto = mapper.convertValue(todo, Todo.class);
        fromDto.setUser(jwtService.getUserFromToken(token));
        final Todo savedTodo = todoRepository.save(fromDto);
        return mapper.convertValue(savedTodo, TodoDto.class);
    }

    public TodoDto editTodo(int id, Map<String, Object> fields, String token) throws JsonMappingException {
        final Todo todoToEdit = todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        if(todoToEdit.getUser() != jwtService.getUserFromToken(token)) {
            throw new TodoNotFoundException();
        }
        final Todo todo = mapper.updateValue(todoToEdit, fields);
        final Todo savedTodo = todoRepository.save(todo);
        return mapper.convertValue(savedTodo, TodoDto.class);
    }

    public Todo changeTodoStatus(int id) {
        final Todo todo = todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        todo.setFinished(!todo.getFinished());
        return todoRepository.save(todo);
    }

    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }

}
