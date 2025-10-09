package com.example.IronCred.controllers;


import com.example.IronCred.dtos.requests.*;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.exceptions.UnauthorizedUserException;
import com.example.IronCred.services.UserServicesImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController {
    @Autowired
    private UserServicesImpl services;

    @PostMapping("/password")
    public ResponseEntity<?> AddPasswords(@Valid @RequestBody AddPasswordRequest request, HttpSession session){
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

    @GetMapping("/password")
    public ResponseEntity<?> getPasswords(HttpSession session){
        String userid;
        try {
            userid = (String) session.getAttribute("userid");
        }catch (IllegalStateException e){
            throw new UnauthorizedUserException("Unauthorized User. Please Login First");
        }

        GetUserPasswordsRequest request = new GetUserPasswordsRequest();
        request.setUserId(userid);
        return ResponseEntity.ok(services.getUserPassword(request));
    }

    @GetMapping("/websites")
    public ResponseEntity<?> getWebsites(HttpSession session){
        String userid;
        try {
            userid = (String) session.getAttribute("userid");
        }catch (IllegalStateException e){
            throw new UnauthorizedUserException("Unauthorized User. Please Login First");
        }

        GetWebsitesRequest request = new GetWebsitesRequest();
        request.setUserId(userid);
        return ResponseEntity.ok(services.getWebsites(request));
    }

    @GetMapping("/passwords")
    public ResponseEntity<?> getPassword(@RequestBody GetAPasswordRequest request, HttpSession session){
        String userid;
        try {
            userid = (String) session.getAttribute("userid");
        }catch (IllegalStateException e){
            throw new UnauthorizedUserException("Unauthorized User. Please Login First");
        }
        request.setUserid(userid);

        return ResponseEntity.ok(services.getPassword(request));

    }

}
