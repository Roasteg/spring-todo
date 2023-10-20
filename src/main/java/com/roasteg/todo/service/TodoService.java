package com.roasteg.todo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
        final Todo fromDto = toEntity(todo);
        fromDto.setUser(jwtService.getUserFromToken(token));
        final Todo savedTodo = todoRepository.save(fromDto);
        return toDto(savedTodo);
    }

    public TodoDto editTodo(int id, Map<String, Object> fields, String token) throws JsonMappingException {
        final Todo todoToEdit = todoRepository.findByUser(jwtService.getUserFromToken(token))
                .stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElseThrow(TodoNotFoundException::new);
        final Todo editedTodo = mapper.updateValue(todoToEdit, fields);
        final Todo savedTodo = todoRepository.save(editedTodo);
        return toDto(savedTodo);
    }

    public TodoDto changeTodoStatus(int id, String token) {
        final Todo todoToFinish = todoRepository.findByUser(jwtService.getUserFromToken(token))
                .stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElseThrow(TodoNotFoundException::new);
        todoToFinish.setFinished(!todoToFinish.getFinished());
        final Todo savedTodo = todoRepository.save(todoToFinish);
        return toDto(savedTodo);
    }

    public void deleteTodo(int id, String token) {
        final Stream<Todo> userTodos = todoRepository.findByUser(jwtService.getUserFromToken(token)).stream();
        if(userTodos.anyMatch(todo -> todo.getId() == id)) {
            todoRepository.deleteById(id);
        } else {
            throw new TodoNotFoundException();
        }
    }

    private TodoDto toDto(Todo todo) {
        return mapper.convertValue(todo, TodoDto.class);
    }

    private Todo toEntity(TodoDto todoDto) {
        return mapper.convertValue(todoDto, Todo.class);
    } 

}
