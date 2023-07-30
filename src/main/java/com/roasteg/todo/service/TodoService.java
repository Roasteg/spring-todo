package com.roasteg.todo.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roasteg.todo.dto.TodoDto;
import com.roasteg.todo.exception.TodoNotFound;
import org.springframework.stereotype.Service;

import com.roasteg.todo.model.Todo;
import com.roasteg.todo.repository.TodoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo editTodo(int id, Map<String, Object> fields) throws JsonMappingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Todo todoToEdit = todoRepository.findById(id).orElseThrow(() -> new TodoNotFound("Todo not found!"));
        final Todo todo = objectMapper.updateValue(todoToEdit, fields);
        return todoRepository.save(todo);
    }

    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }

}
