package com.example.IronCred.exceptions;

public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException() {}

    public UserDoesntExistException(String message) {
        super(message);
    }
}
