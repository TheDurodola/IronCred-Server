package com.example.IronCred.dtos.responses;

import lombok.Data;



@Data
public class GetPasswordResponse {
    private String password;
    private String username;
    private String website;
    private String createdAt;
    private String updatedAt;
}
