package com.example.IronCred.services;

import com.example.IronCred.data.models.User;
import com.example.IronCred.data.repositories.Users;
import com.example.IronCred.dtos.requests.DeleteUserRequest;
import com.example.IronCred.dtos.requests.LoginRequest;
import com.example.IronCred.dtos.requests.RegistrationRequest;
import com.example.IronCred.dtos.responses.LoginResponse;
import com.example.IronCred.dtos.responses.RegistrationResponse;
import com.example.IronCred.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.IronCred.utils.Mapper.map;


@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private Users users;

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegistrationResponse registeredUser(RegistrationRequest request) {
        request.setEmail(request.getEmail().toLowerCase());
        request.setUsername(request.getUsername().toLowerCase());
        verifyEmailAndUsername(request);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return map(users.save(map(request)));
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        request.setUsername(request.getUsername().toLowerCase());
        User user = users.findByUsername(request.getUsername()).orElseThrow(UserDoesntExistException::new);

       String password = user.getPassword();

       if (!passwordEncoder.matches(request.getPassword(), password)) {
           throw new InvalidPasswordException("Invalid Password while logging in");
       }

       LoginResponse response = new LoginResponse();
       response.setUsername(request.getUsername());
       response.setId(user.getId());
       return response;
    }


    @Override
    public Optional<User> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteUser(DeleteUserRequest request) {
        User user = users.findById(request.getId()).get();
        validateUserDetails(request, user);
        users.delete(user);
    }

    private static void validateUserDetails(DeleteUserRequest request, User user) {
        if(!user.getUsername().equalsIgnoreCase(request.getUsername())){
            throw new InvalidUsernameException("Invalid Username");
        }
        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) ){
            throw new InvalidEmailException("Invalid Email");
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("Invalid Password");
        }
    }
    private void verifyEmailAndUsername(RegistrationRequest request) {


        users.findByEmail(request.getEmail()).ifPresent(user -> {throw new UserAlreadyExistException("Email already exists");});
        users.findByUsername(request.getUsername()).ifPresent(user -> {throw new UserAlreadyExistException("Username already exists");});
    }
}
