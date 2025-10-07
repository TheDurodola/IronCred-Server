package com.example.IronCred.services;

import com.example.IronCred.data.models.User;
import com.example.IronCred.dtos.requests.DeleteUserRequest;
import com.example.IronCred.dtos.requests.LoginRequest;
import com.example.IronCred.dtos.requests.RegistrationRequest;
import com.example.IronCred.dtos.responses.LoginResponse;
import com.example.IronCred.dtos.responses.LogoutResponse;
import com.example.IronCred.dtos.responses.RegistrationResponse;


import java.util.Optional;

public interface AuthServices {
    RegistrationResponse registeredUser(RegistrationRequest request);
    LoginResponse login(LoginRequest request);
    LogoutResponse logout();
    Optional<User> getUserById(String id);
    void deleteById(DeleteUserRequest request);
}
