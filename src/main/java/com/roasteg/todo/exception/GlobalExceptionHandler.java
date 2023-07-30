package com.roasteg.todo.exception;

import com.roasteg.todo.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> todoNotFound(TodoNotFoundException exception) {
        final Response customException = new Response();
        customException.setMessage("Todo not found!");
        customException.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Response> userNotFound(UserNotFoundException exception) {
        final Response customException = new Response();
        customException.setMessage("User not found!");
        customException.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Response> userExists(UserExistsException exception) {
        final Response customException = new Response();
        customException.setMessage(exception.getMessage());
        customException.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(customException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Response> incorrectCredentials(IncorrectCredentialsException exception) {
        final Response customException = new Response();
        customException.setMessage(exception.getMessage());
        customException.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(customException, HttpStatus.UNAUTHORIZED);
    }
}
