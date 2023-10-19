package com.roasteg.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roasteg.todo.model.Todo;
import com.roasteg.todo.model.TodoUser;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByUser(TodoUser user);
}
