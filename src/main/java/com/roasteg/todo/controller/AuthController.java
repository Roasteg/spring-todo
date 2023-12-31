package com.roasteg.todo.controller;

import com.roasteg.todo.dto.AuthDto;
import com.roasteg.todo.dto.UserDto;
import com.roasteg.todo.exception.IncorrectCredentialsException;
import com.roasteg.todo.exception.UserExistsException;
import com.roasteg.todo.model.TodoUser;
import com.roasteg.todo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody AuthDto request) {
        if (authService.userExistsWithEmail(request.getEmail())) {
            throw new UserExistsException();
        }
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody AuthDto request) {
        final TodoUser user = authService.findByEmail(request.getEmail());
        if (!authService.passwordsMatching(user, request)) {
            throw new IncorrectCredentialsException();
        }

        return ResponseEntity.ok(authService.login(request));
    }

}
