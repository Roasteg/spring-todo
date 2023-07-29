package com.roasteg.todo.service;

import java.util.List;

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
}
