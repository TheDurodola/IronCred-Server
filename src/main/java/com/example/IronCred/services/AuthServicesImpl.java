package com.example.IronCred.services;

import com.example.IronCred.data.models.User;
import com.example.IronCred.data.repositories.Users;
import com.example.IronCred.dtos.requests.DeleteUserRequest;
import com.example.IronCred.dtos.requests.LoginRequest;
import com.example.IronCred.dtos.requests.RegistrationRequest;
import com.example.IronCred.dtos.responses.LoginResponse;
import com.example.IronCred.dtos.responses.LogoutResponse;
import com.example.IronCred.dtos.responses.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private Users users;

    @Override
    public RegistrationResponse registeredUser(RegistrationRequest request) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public LogoutResponse logout() {
        return null;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(DeleteUserRequest request) {

    }
}
