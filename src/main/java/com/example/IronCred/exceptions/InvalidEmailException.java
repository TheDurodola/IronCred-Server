package com.example.IronCred.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String input) {
        super(input);
    }
}
