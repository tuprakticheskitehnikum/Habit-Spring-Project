package com.example.demo.payload.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class ErrorResponse {
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss-ns")
    private final LocalDateTime timestamp;

    private final HttpStatus status;

    private final String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}
