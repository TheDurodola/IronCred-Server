package com.example.IronCred.dtos.responses;

import lombok.Data;

@Data
public class GetAPasswordResponse {
    private String id;
    private String username;
    private String password;
    private String website;
    private String createdAt;
}
