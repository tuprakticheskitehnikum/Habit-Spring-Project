package com.example.demo.exception;

public class HabitNotFoundException extends RuntimeException {

    public HabitNotFoundException() {
    }

    public HabitNotFoundException(String arg0) {
        super(arg0);
    }

    public HabitNotFoundException(Throwable arg0) {
        super(arg0);
    }
}
