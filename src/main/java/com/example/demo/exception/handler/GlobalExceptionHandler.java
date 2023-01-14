package com.example.demo.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.demo.exception.HabitNotFoundException;
import com.example.demo.payload.response.ErrorResponse;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice(basePackages = {"com.example.demo.controller"})
public class GlobalExceptionHandler {
    @ExceptionHandler(value = HabitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(HabitNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND, exception.getMessage()));
    }
}
