package com.example.IronCred.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String invalidPassword) {
        super(invalidPassword);
    }
}
