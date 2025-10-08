package com.example.IronCred.dtos.requests;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class DeleteUserRequest {
    private String id;
    private String username;
    @Email
    private String email;
    private String password;
}
