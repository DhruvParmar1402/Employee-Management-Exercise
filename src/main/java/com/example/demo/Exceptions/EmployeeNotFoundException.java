package com.example.demo.Exceptions;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String msg) {
        super(msg);
    }
}
