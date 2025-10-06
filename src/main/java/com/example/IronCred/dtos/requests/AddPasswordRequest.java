package com.example.IronCred.dtos.requests;


import lombok.Data;

@Data
public class AddPasswordRequest {
    private String username;
    private String password;
    private String website;
}
