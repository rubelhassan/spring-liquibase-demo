package com.example.springliquibasedemo.modules.task;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
