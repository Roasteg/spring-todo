package com.roasteg.todo.exception;

import com.roasteg.todo.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> todoNotFound(TodoNotFound exception) {
        final Response customException = new Response();
        customException.setMessage("Todo not found!");
        customException.setStatus(HttpStatus.NOT_FOUND.value());
        customException.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<Response>(customException, HttpStatus.NOT_FOUND);
    }
}
