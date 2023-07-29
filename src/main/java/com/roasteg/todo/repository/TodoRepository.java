package com.roasteg.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roasteg.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
