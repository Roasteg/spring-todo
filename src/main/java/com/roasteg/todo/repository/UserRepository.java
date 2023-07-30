package com.roasteg.todo.repository;


import com.roasteg.todo.model.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TodoUser, Integer> {
    Optional<TodoUser> findByEmail(String email);
}
