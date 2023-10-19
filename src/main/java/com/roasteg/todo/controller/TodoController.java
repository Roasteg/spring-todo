package com.roasteg.todo.controller;


import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.roasteg.todo.dto.TodoDto;
import com.roasteg.todo.model.Response;
import com.roasteg.todo.model.Todo;
import com.roasteg.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<TodoDto> getTodos(@RequestHeader(name = "Authorization") String token) {
        return todoService.getAllTodos(token);
    }

    @PostMapping("/add")
    public TodoDto addTodo(@RequestBody TodoDto todo, @RequestHeader(name = "Authorization") String token) {
        return todoService.saveTodo(todo, token);
    }

    @PostMapping("/finish/{id}")
    public Todo finishTodo(@PathVariable int id, @RequestHeader(name = "Authorization") String token) {
        return todoService.changeTodoStatus(id);
    }

    @PatchMapping("/edit/{id}")
    public TodoDto editTodo(@PathVariable int id, @RequestBody Map<String, Object> fields, @RequestHeader(name = "Authorization") String token) throws JsonMappingException {
        return todoService.editTodo(id, fields, token);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTodo(@PathVariable int id, @RequestHeader(name = "Authorization") String token) {
        todoService.deleteTodo(id);
    }
}
