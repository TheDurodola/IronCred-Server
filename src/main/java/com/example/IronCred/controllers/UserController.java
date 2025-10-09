package com.example.IronCred.controllers;


import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.requests.LoginRequest;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.dtos.responses.LoginResponse;
import com.example.IronCred.exceptions.UnauthorizedUserException;
import com.example.IronCred.services.UserServicesImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class UserController {
    @Autowired
    private UserServicesImpl services;

    @PostMapping("/password")
    public ResponseEntity<?> login(@Valid @RequestBody AddPasswordRequest request, HttpSession session){
        String userid;
        try {
            userid = (String) session.getAttribute("userid");
        }catch (IllegalStateException e){
            throw new UnauthorizedUserException("Unauthorized User. Please Login First");
        }

        request.setUserId(userid);
        AddPasswordResponse response = services.addPassword(request);
        return ResponseEntity.ok(response);
    }

}
