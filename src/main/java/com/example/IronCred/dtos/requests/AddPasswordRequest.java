package com.example.IronCred.dtos.requests;


import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AddPasswordRequest {
    private String username;
    private String password;
    @URL
    private String website;
}
