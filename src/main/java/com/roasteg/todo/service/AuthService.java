package com.roasteg.todo.service;

import com.roasteg.todo.exception.UserNotFoundException;
import com.roasteg.todo.model.Role;
import com.roasteg.todo.model.TodoUser;
import com.roasteg.todo.repository.UserRepository;
import com.roasteg.todo.security.AuthRequest;
import com.roasteg.todo.security.AuthResponse;
import com.roasteg.todo.security.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(SignupRequest signupRequest) {
        final TodoUser user = TodoUser
                .builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        userRepository.save(user);
        final String token = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .token(token)
                .name(signupRequest.getName())
                .build();
    }

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        TodoUser user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .token(token)
                .name(user.getName())
                .build();
    }

    public TodoUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public TodoUser findById(int id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public boolean userExistsWithEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean passwordsMatching(TodoUser user, AuthRequest authRequest) {
        return passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
    }
}
