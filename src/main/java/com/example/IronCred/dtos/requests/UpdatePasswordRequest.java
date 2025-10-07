package com.example.IronCred.dtos.requests;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String id;
    private String password;
    private String username;
    private String website;
}
