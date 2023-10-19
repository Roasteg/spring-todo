package com.roasteg.todo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roasteg.todo.dto.UserDto;
import com.roasteg.todo.exception.UserNotFoundException;
import com.roasteg.todo.model.Role;
import com.roasteg.todo.model.TodoUser;
import com.roasteg.todo.repository.UserRepository;
import com.roasteg.todo.dto.AuthDto;
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

    private final ObjectMapper mapper;

    public UserDto register(AuthDto authDto) {
        final TodoUser user = TodoUser
                .builder()
                .name(authDto.getName())
                .email(authDto.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(authDto.getPassword()))
                .build();
        userRepository.save(user);
        final String token = jwtService.generateToken(user);
        final UserDto userDto = mapper.convertValue(user, UserDto.class);
        userDto.setToken(token);
        return userDto;
    }

    public UserDto login(AuthDto authDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.getEmail(),
                        authDto.getPassword()));
        TodoUser user = userRepository.findByEmail(authDto.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        UserDto userDto = mapper.convertValue(user, UserDto.class);
        userDto.setToken(token);
        return userDto;
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

    public boolean passwordsMatching(TodoUser user, AuthDto authDto) {
        return passwordEncoder.matches(authDto.getPassword(), user.getPassword());
    }
}
