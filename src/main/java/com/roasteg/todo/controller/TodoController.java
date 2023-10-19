package com.roasteg.todo.controller;


import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.web.bind.annotation.*;

import com.roasteg.todo.model.Todo;
import com.roasteg.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping("/add")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.saveTodo(todo);
    }

    @PostMapping("/finish/{id}")
    public Todo finishTodo(@PathVariable int id) {
        return todoService.changeTodoStatus(id);
    }

    @PatchMapping("/edit/{id}")
    public Todo editTodo(@PathVariable int id, @RequestBody Map<String, Object> fields) throws JsonMappingException {
        return todoService.editTodo(id, fields);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTodo(@PathVariable int id) {
        todoService.deleteTodo(id);
    }
}
