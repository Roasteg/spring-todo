package com.roasteg.todo.exception;

import com.roasteg.todo.model.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomException> todoNotFound(TodoNotFound exception) {
        final CustomException customException = new CustomException();
        customException.setMessage(exception.getMessage());
        customException.setStatus(HttpStatus.NOT_FOUND.value());
        customException.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<CustomException>(customException, HttpStatus.NOT_FOUND);
    }
}
