package com.example.IronCred.exceptions;

public class AESDecryptionException extends RuntimeException {
    public AESDecryptionException(String message) {
        super(message);
    }
}
