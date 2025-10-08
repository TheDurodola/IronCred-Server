package com.example.IronCred.dtos.responses;


import com.example.IronCred.data.models.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String id;
}
